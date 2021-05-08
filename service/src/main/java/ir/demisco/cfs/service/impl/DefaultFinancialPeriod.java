package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusDto;
import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeAssignRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.core.utils.DateUtil;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {

    private final GridFilterService gridFilterService;
    private final FinancialPeriodListGridProvider financialPeriodListGridProvider;
    private final FinancialPeriodStatusRepository financialPeriodStatusRepository;
    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;
    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultFinancialPeriod(GridFilterService gridFilterService, FinancialPeriodListGridProvider financialPeriodListGridProvider, FinancialPeriodStatusRepository financialPeriodStatusRepository, FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository, FinancialPeriodRepository financialPeriodRepository) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodListGridProvider = financialPeriodListGridProvider;
        this.financialPeriodStatusRepository = financialPeriodStatusRepository;
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }


    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(organizationId, "organizationId is null");
        dataSourceRequest.setFilter(DataSourceRequest.FilterDescriptor.create("financialPeriodTypeAssign.organization.id", organizationId));
        return gridFilterService.filter(dataSourceRequest, financialPeriodListGridProvider);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Long save(FinancialPeriodDto financialPeriodDto, String mode) {
        validationSave(financialPeriodDto, mode);
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId() == null ? 0L : financialPeriodDto.getId()).orElse(new FinancialPeriod());
        financialPeriod.setEndDate(financialPeriodDto.getEndDate());
        if (!mode.equals("update")) {
            financialPeriod.setStartDate(financialPeriodDto.getStartDate());
        }
        financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(1L));
        financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssignRepository.getOne(financialPeriodDto.getFinancialPeriodTypeAssignId()));
        return financialPeriodRepository.save(financialPeriod).getId();
    }


    private void validationSave(FinancialPeriodDto financialPeriodDto, String mode) {
//        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(1L, "OPEN");
        if (period.size() >= 2) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی فعال نمی توان ایجاد کرد");
        } else if (period.size() == 1 && !mode.equals("update")) {
            Date endDate = period.get(0).getEndDate();
            financialPeriodDto.setStartDate(DateUtil.addMonth(endDate, 1, Locale.ENGLISH));
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches("1[0-2]|[1-9]")) {
            throw new RuleException("تعداد ماه قابل ویرایش میبایست بین 1 تا 12 باشد.");
        }
//        Long countFinancialTypeAssign = financialPeriodRepository.getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId
//                (financialPeriodDto.getStartDate(), financialPeriodDto.getEndDate(), financialPeriodDto.getFinancialPeriodTypeAssignId());
//        if (countFinancialTypeAssign > 0) {
//            throw new RuleException("fin.validate.month.startDate.endDate");
//        }
        if (mode.equals("update") && financialPeriodDto.getStartDate() != null) {
            throw new RuleException("تاریخ شروع قابل ویرایش نیست.");
        }
        if (mode.equals("save") && financialPeriodDto.getStartDate().after(financialPeriodDto.getEndDate())) {
            throw new RuleException("تاریخ شروع نمیتواند از تاریخ پایان بزرگتر باشد.");
        }

    }

    @Transactional
    @Override
    public FinancialPeriodDto changeStatusFinancialPeriodById(Long financialPeriodId) {
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodId).orElseThrow(() -> new RuleException("هیچ دوره ی مالی یافت نشد."));
//        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(1L));
        FinancialPeriodDto financialPeriodDto = FinancialPeriodDto.builder().startDate(
                financialPeriod.getStartDate())
                .endDate(financialPeriod.getEndDate()).openMonthCount(financialPeriod.getOpenMonthCount())
                .financialPeriodStatusDto(FinancialPeriodStatusDto.builder().id(financialPeriod.getFinancialPeriodStatus().getId()).name(financialPeriod.getFinancialPeriodStatus().getName()).build())
                .build();
        validationSave(financialPeriodDto, null);
        return financialPeriodDto;
    }

}


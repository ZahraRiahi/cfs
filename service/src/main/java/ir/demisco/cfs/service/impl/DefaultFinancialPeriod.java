package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeAssignRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest
                .FilterDescriptor.create("financialPeriodTypeAssign.organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        return gridFilterService.filter(dataSourceRequest, financialPeriodListGridProvider);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Long save(FinancialPeriodDto financialPeriodDto) {
        validationSave(financialPeriodDto);
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId() == null ? 0L : financialPeriodDto.getId()).orElse(new FinancialPeriod());
        financialPeriod.setEndDate(financialPeriodDto.getEndDate());
        financialPeriod.setStartDate(financialPeriodDto.getStartDate());
        financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(1L));
//        financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssignRepository.getOne(financialPeriodDto.getFinancialPeriodTypeAssignId()));
        financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignId(1L).orElseThrow(() -> new RuleException("برای این سازمان هیچ نوع دوره ی مالی وجود ندارد.")));
        return financialPeriodRepository.save(financialPeriod).getId();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialPeriodDto update(FinancialPeriodDto financialPeriodDto) {
        validationUpdate(financialPeriodDto, "start");
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("برای انجام عملیات ویرایش شناسه ی دوره ی مالی الزامی میباشد."));
        financialPeriod.setEndDate(financialPeriodDto.getEndDate());
        financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(financialPeriodDto.getStatusId()));
//        financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssignRepository.getOne(financialPeriodDto.getFinancialPeriodTypeAssignId()));
        financialPeriod = financialPeriodRepository.save(financialPeriod);
        validationUpdate(financialPeriodDto, "end");
        return convertFinancialPeriodToDto(financialPeriod);
    }

    private void validationUpdate(FinancialPeriodDto financialPeriodDto, String mode) {
        if (financialPeriodDto.getId() == null && mode.equals("start")) {
            throw new RuleException("برای انجام عملیات ویرایش شناسه ی دوره ی مالی الزامی میباشد.");
        }
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(1L, "OPEN");
        if (period.size() >= 3 && mode.equals("end")) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی باز وجود ندارد.");
        } else if (period.size() > 2 && mode.equals("change")) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی باز وجود ندارد.");
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches("1[0-2]|[1-9]")) {
            throw new RuleException("تعداد ماه قابل ویرایش میبایست بین 1 تا 12 باشد.");
        }
        if (financialPeriodDto.getStartDate() != null && mode.equals("start")) {
            throw new RuleException("تاریخ شروع قابل ویرایش نیست.");
        }
//        Long countFinancialPeriod = financialPeriodRepository.getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(financialPeriodDto.getEndDate(), financialPeriodDto.getFinancialPeriodTypeAssignId());
//        if (countFinancialPeriod > 0 && mode.equals("start")) {
//            throw new RuleException("دروه ی مالی برای این سازمان با این تاریخ پایان موجود میباشد.");
//        }
    }

    private void validationSave(FinancialPeriodDto financialPeriodDto) {
//        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(2L, "OPEN");
        if (period.size() >= 2) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی باز نمی توان ایجاد کرد");
        } else if (period.size() == 1) {
//            financialPeriodDto.setFinancialPeriodTypeAssignId(period.get(0).getFinancialPeriodTypeAssign().getId());
            financialPeriodDto.setStartDate(financialPeriodDto.getEndDate().plusMonths(1));
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches("1[0-2]|[1-9]")) {
            throw new RuleException("تعداد ماه قابل ویرایش میبایست بین 1 تا 12 باشد.");
        }
        if (financialPeriodDto.getStartDate().isAfter(financialPeriodDto.getEndDate())) {
            throw new RuleException("تاریخ شروع نمیتواند از تاریخ پایان بزرگتر باشد.");
        }
        Long financialCount = financialPeriodRepository.getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(financialPeriodDto.getStartDate(), financialPeriodDto.getEndDate(), financialPeriodDto.getFinancialPeriodTypeAssignId());
        if (financialCount > 0) {
            throw new RuleException("برای این سازمان، دوره ی مالی با تاریخ شروع یا پایان قبلا ثبت شده است.");
        }
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialPeriodDto changeStatusFinancialPeriodById(Long financialPeriodId) {
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodId).orElseThrow(() -> new RuleException("هیچ دوره ی مالی یافت نشد."));
        if (financialPeriod.getFinancialPeriodStatus().getId().equals(1L)) {
            financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(2L));
        } else {
            financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(1L));
        }
        financialPeriodRepository.save(financialPeriod);
        FinancialPeriodDto financialPeriodDto = convertFinancialPeriodToDto(financialPeriod);
        validationUpdate(financialPeriodDto, "change");
        return financialPeriodDto;
    }

    private FinancialPeriodDto convertFinancialPeriodToDto(FinancialPeriod financialPeriod) {
        return FinancialPeriodDto.builder().startDate(
                financialPeriod.getStartDate())
                .endDate(financialPeriod.getEndDate()).openMonthCount(financialPeriod.getOpenMonthCount())
                .statusId(financialPeriod.getFinancialPeriodStatus().getId())
                .statusName(financialPeriod.getFinancialPeriodStatus().getName())
                .build();

    }
}


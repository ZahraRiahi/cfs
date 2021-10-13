package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDateDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.model.entity.FinancialMonth;
import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cfs.model.entity.FinancialPeriodParameter;
import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {

    private final GridFilterService gridFilterService;
    private final FinancialPeriodListGridProvider financialPeriodListGridProvider;
    private final FinancialPeriodStatusRepository financialPeriodStatusRepository;
    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialMonthTypeRepository financialMonthTypeRepository;
    private final FinancialMonthRepository financialMonthRepository;
    private final FinancialMonthStatusRepository financialMonthStatusRepository;
    private final FinancialPeriodParameterRepository periodParameterRepository;

    public DefaultFinancialPeriod(GridFilterService gridFilterService, FinancialPeriodListGridProvider financialPeriodListGridProvider, FinancialPeriodStatusRepository financialPeriodStatusRepository, FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository, FinancialPeriodRepository financialPeriodRepository, FinancialMonthTypeRepository financialMonthTypeRepository, FinancialMonthRepository financialMonthRepository, FinancialMonthStatusRepository financialMonthStatusRepository, FinancialPeriodParameterRepository periodParameterRepository) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodListGridProvider = financialPeriodListGridProvider;
        this.financialPeriodStatusRepository = financialPeriodStatusRepository;
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialMonthTypeRepository = financialMonthTypeRepository;
        this.financialMonthRepository = financialMonthRepository;
        this.financialMonthStatusRepository = financialMonthStatusRepository;
        this.periodParameterRepository = periodParameterRepository;
    }

    private static FinancialPeriodDateDto apply(Object[] object) {
        return FinancialPeriodDateDto.builder()
                .startDate(DateUtil.convertStringToDate(object[0].toString()))
                .endDate(DateUtil.convertStringToDate(object[1].toString()))
                .build();
    }


    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(organizationId, "organizationId is null");
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest
                .FilterDescriptor.create("financialPeriodTypeAssign.organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        return gridFilterService.filter(dataSourceRequest, financialPeriodListGridProvider);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialPeriodDto save(FinancialPeriodDto financialPeriodDto) {
        validationSave(financialPeriodDto);
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId() == null ? 0L : financialPeriodDto.getId()).orElse(new FinancialPeriod());
        financialPeriod.setEndDate(financialPeriodDto.getEndDate().truncatedTo(ChronoUnit.DAYS));
        financialPeriod.setStartDate(financialPeriodDto.getStartDate().truncatedTo(ChronoUnit.DAYS));
        financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(1L));

        FinancialPeriodTypeAssign financialPeriodTypeAssign = financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignIdAndOrgan(organizationId).orElseThrow(() -> new RuleException("برای این سازمان هیچ نوع دوره ی مالی فعال وجود ندارد."));
        financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssign);
        financialPeriod.setCode(financialPeriodRepository.getCodeFinancialPeriod(organizationId));
        financialPeriod.setDescription(financialPeriodRepository.getDescriptionFinancialPeriod(financialPeriodDto.getEndDate().toString().split("T")[0]));

        financialPeriod = financialPeriodRepository.save(financialPeriod);

        List<Object[]> list = financialMonthTypeRepository.findByParam(organizationId, financialPeriod.getId());
        FinancialPeriod finalFinancialPeriod = financialPeriod;
        list.forEach(item -> {
            FinancialMonth financialMonth = new FinancialMonth();
            financialMonth.setFinancialPeriod(finalFinancialPeriod);
            financialMonth.setFinancialMonthType(financialMonthTypeRepository.getOne(Long.parseLong(item[0].toString())));
            financialMonth.setFinancialMonthStatus(financialMonthStatusRepository.getOne(1L));
            financialMonth.setStartDate(DateUtil.convertStringToDate(item[1].toString()));
            financialMonth.setEndDate(DateUtil.convertStringToDate(item[2].toString()));
            financialMonth.setDescription(item[3].toString());
            financialMonthRepository.save(financialMonth);
        });
        List<Object[]> periodParameters = periodParameterRepository.getPeriodParameterByPeriodId(organizationId, finalFinancialPeriod.getId());
        periodParameters.forEach(objects -> {
            FinancialPeriodParameter financialPeriodParameter = new FinancialPeriodParameter();
            financialPeriodParameter.setFinancialPeriod(finalFinancialPeriod);
            financialPeriodParameter.setStartDate((Date) objects[1]);
            financialPeriodParameter.setTaxDeductionRate(Long.parseLong(objects[2].toString()));
            financialPeriodParameter.setVatTaxRate(Long.parseLong(objects[3].toString()));
            financialPeriodParameter.setVatTollRate(Long.parseLong(objects[4].toString()));
            financialPeriodParameter.setInsuranceDeductionRate(Long.parseLong(objects[5].toString()));
            financialPeriodParameter.setMaxFewerAmount(Long.parseLong(objects[6].toString()));
            int flag = Integer.parseInt(objects[7].toString());
            if (flag == 1) {
                financialPeriodParameter.setVatFillFlag(true);
            } else {
                financialPeriodParameter.setVatFillFlag(false);
            }
            periodParameterRepository.save(financialPeriodParameter);
        });
        return convertFinancialPeriodToDto(financialPeriod);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialPeriodDto update(FinancialPeriodDto financialPeriodDto) {
        validationUpdate(financialPeriodDto, "start");
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("برای انجام عملیات ویرایش شناسه ی دوره ی مالی الزامی میباشد."));
        financialPeriod.setStartDate(financialPeriodDto.getStartDate());
        financialPeriod.setEndDate(financialPeriodDto.getEndDate());
        financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(financialPeriodDto.getStatusId()));
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialPeriod.setCode(financialPeriodRepository.getCodeFinancialPeriod(organizationId));
        financialPeriod.setDescription(financialPeriodRepository.getDescriptionFinancialPeriod(financialPeriodDto.getEndDate().toString().split("T")[0]));
        financialPeriod = financialPeriodRepository.save(financialPeriod);
        validationUpdate(financialPeriodDto, "end");
        return convertFinancialPeriodToDto(financialPeriod);
    }


    private void validationUpdate(FinancialPeriodDto financialPeriodDto, String mode) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("هیچ دوره ی مالی یافت نشد."));
        if (financialPeriodDto.getId() == null && mode.equals("start")) {
            throw new RuleException("برای انجام عملیات ویرایش شناسه ی دوره ی مالی الزامی میباشد.");
        }
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId, "OPEN");
        if (period.size() >= 3 && mode.equals("end")) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی باز وجود ندارد.");
        } else if (period.size() > 2 && mode.equals("change")) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی باز وجود ندارد.");
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches("1[0-2]|[1-9]")) {
            throw new RuleException("تعداد ماه قابل ویرایش میبایست بین 1 تا 12 باشد.");
        }
        if (financialPeriod.getStartDate() != null && !financialPeriod.getStartDate().equals(financialPeriodDto.getStartDate())) {
            if (mode.equals("start")) {
                throw new RuleException("تاریخ شروع قابل ویرایش نیست.");
            }
        }
    }


    private void validationSave(FinancialPeriodDto financialPeriodDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId, "OPEN");
        List<FinancialPeriod> periodStartDate = financialPeriodRepository.findByFinancialPeriodGetStartDateOrganizationId(organizationId);
        if (period.size() >= 2) {
            throw new RuleException("برای هر سازمان بیش از 2 دوره مالی باز نمی توان ایجاد کرد");
        } else if (periodStartDate.size() > 0) {
            financialPeriodDto.setFinancialPeriodTypeAssignId(periodStartDate.get(0).getFinancialPeriodTypeAssign().getId());
        } else {
            FinancialPeriodTypeAssign financialPeriodTypeAssign =
                    financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignId(organizationId).orElseThrow(() -> new RuleException("برای این سازمان هیچ نوع دوره ی مالی وجود ندارد."));
            financialPeriodDto.setFinancialPeriodTypeAssignId(financialPeriodTypeAssign.getId());
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
    public FinancialPeriodDto changeStatusFinancialPeriodById(FinancialPeriodDto financialPeriodDto) {
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("هیچ دوره ی مالی یافت نشد."));
        validationBeforeChangeStatus(financialPeriod.getId(), financialPeriodDto);
        financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(financialPeriodDto.getStatusId()));
        financialPeriodRepository.save(financialPeriod);
        FinancialPeriodDto financialPeriodDto1 = convertFinancialPeriodToDto(financialPeriod);
        validationUpdate(financialPeriodDto1, "change");
        return financialPeriodDto1;
    }

    private void validationBeforeChangeStatus(Long financialPeriodId, FinancialPeriodDto financialPeriodDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        if (financialPeriodDto.getStatusId() == 2) {
            Long existOpen = financialPeriodRepository.checkFinancialStatusIdIsOpen(financialPeriodId, organizationId);
            if (existOpen != null && existOpen == 1) {
                throw new RuleException("به دلیل باز بودن دوره مالی قبلی ، امکان بستن این دوره مالی وجود ندارد");
            }
        } else if (financialPeriodDto.getStatusId() == 1) {
            Long exitClose = financialPeriodRepository.checkFinancialStatusIdIsClose(financialPeriodId, organizationId);
            if (exitClose != null && exitClose == 1) {
                throw new RuleException("به دلیل وجود دوره مالی بسته ، بعد از این دوره مالی ، امکان باز کردن این دوره مالی وجود ندارد’");

            }
        }

    }

    @Override
    @Transactional
    public FinancialPeriodDateDto getStartDateFinancialPeriod(Long organizationId) {

        List<Object[]> objects = financialPeriodTypeAssignRepository.getStartDateAndEndDate(organizationId);
        if (objects.isEmpty()) {
            throw new RuleException("لطفا نوع دوره مالی سازمان را مشخص کنید.");
        }
        return objectToDto(objects);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest
                                                                                   financialPeriodRequest, Long organizationId) {
        List<Object[]> financialPeriodListObject = financialPeriodRepository.findByFinancialPeriodAndDate(financialPeriodRequest.getDate().toString(), organizationId);
        return financialPeriodListObject.stream().map(objects -> FinancialPeriodResponse.builder().id(Long.parseLong(objects[0].toString()))
                .description(objects[2] == null ? null : objects[2].toString())
                .code(objects[3] == null ? null : objects[3].toString())
                .fullDescription(objects[1].toString())
                .build()).collect(Collectors.toList());
    }

    private FinancialPeriodDateDto objectToDto(List<Object[]> objects) {
        return objects.stream().map(DefaultFinancialPeriod::apply).collect(Collectors.toList()).get(0);
    }


    private FinancialPeriodDto convertFinancialPeriodToDto(FinancialPeriod financialPeriod) {
        return FinancialPeriodDto.builder()
                .id(financialPeriod.getId())
                .startDate(financialPeriod.getStartDate())
                .endDate(financialPeriod.getEndDate()).openMonthCount(financialPeriod.getOpenMonthCount())
                .statusId(financialPeriod.getFinancialPeriodStatus().getId())
                .statusName(financialPeriod.getFinancialPeriodStatus().getName())
                .statusCode(financialPeriod.getFinancialPeriodStatus().getCode())
                .description(financialPeriod.getDescription())
                .code(financialPeriod.getCode())
                .financialPeriodTypeAssignId(financialPeriod.getFinancialPeriodTypeAssign().getId())
                .build();
    }
}


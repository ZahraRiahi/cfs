package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final FinancialDocumentRepository financialDocumentRepository;

    public DefaultFinancialPeriod(GridFilterService gridFilterService, FinancialPeriodListGridProvider financialPeriodListGridProvider, FinancialPeriodStatusRepository financialPeriodStatusRepository, FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository, FinancialPeriodRepository financialPeriodRepository, FinancialMonthTypeRepository financialMonthTypeRepository, FinancialMonthRepository financialMonthRepository, FinancialMonthStatusRepository financialMonthStatusRepository, FinancialPeriodParameterRepository periodParameterRepository, FinancialDocumentRepository financialDocumentRepository) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodListGridProvider = financialPeriodListGridProvider;
        this.financialPeriodStatusRepository = financialPeriodStatusRepository;
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialMonthTypeRepository = financialMonthTypeRepository;
        this.financialMonthRepository = financialMonthRepository;
        this.financialMonthStatusRepository = financialMonthStatusRepository;
        this.periodParameterRepository = periodParameterRepository;
        this.financialDocumentRepository = financialDocumentRepository;
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
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialPeriodTypeAssign.deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialPeriodStatus.deletedDate", null, DataSourceRequest.Operators.IS_NULL));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("financialPeriodTypeAssign.activeFlag", 1, DataSourceRequest.Operators.EQUALS));
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

        FinancialPeriodTypeAssign financialPeriodTypeAssign = financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignIdAndOrgan(organizationId).orElseThrow(() -> new RuleException("fin.financialPeriod.financialPeriodTypeAssignIdAndOrgan"));
        financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssign);
        financialPeriod.setCode(financialPeriodRepository.getCodeFinancialPeriod(organizationId));
        financialPeriod.setDescription(financialPeriodRepository.getDescriptionFinancialPeriod(financialPeriodDto.getEndDate().toString().split("T")[0]));

        financialPeriod = financialPeriodRepository.save(financialPeriod);
        List<Object[]> list = financialMonthTypeRepository.findByParam(organizationId, financialPeriod.getId());
        FinancialPeriod finalFinancialPeriod = financialPeriod;
        list.forEach(item -> {
            FinancialMonth financialMonth = new FinancialMonth();
            financialMonth.setFinancialPeriod(finalFinancialPeriod);
            financialMonth.setFinancialMonthType(financialMonthTypeRepository.findById(Long.parseLong(item[0].toString())).orElse(null));
            financialMonth.setFinancialMonthStatus(financialMonthStatusRepository.findById(1L).orElse(null));
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
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("fin.financialPeriod.update"));
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
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("fin.financialPeriod.notFound"));
        if (financialPeriodDto.getId() == null && mode.equals("start")) {
            throw new RuleException("fin.financialPeriod.update");
        }
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId, "OPEN");
        if (period.size() >= 3 && mode.equals("end")) {
            throw new RuleException("fin.financialPeriod.validationUpdate");
        } else if (period.size() > 2 && mode.equals("change")) {
            throw new RuleException("fin.financialPeriod.validationUpdate");
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches("1[0-2]|[1-9]")) {
            throw new RuleException("fin.financialPeriod.financialMonth.update");
        }
        if (financialPeriod.getStartDate() != null && !financialPeriod.getStartDate().equals(financialPeriodDto.getStartDate())) {
            if (mode.equals("end")) {
                throw new RuleException("fin.financialPeriod.financialMonthStartDate.update");
            }
        }
    }

    private void validationSave(FinancialPeriodDto financialPeriodDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        List<FinancialPeriod> period = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId, "OPEN");
        List<FinancialPeriod> periodStartDate = financialPeriodRepository.findByFinancialPeriodGetStartDateOrganizationId(organizationId);
        if (period.size() >= 2) {
            throw new RuleException("fin.financialPeriod.validationSave");
        } else if (periodStartDate.size() > 0) {
            financialPeriodDto.setFinancialPeriodTypeAssignId(periodStartDate.get(0).getFinancialPeriodTypeAssign().getId());
        } else {
            FinancialPeriodTypeAssign financialPeriodTypeAssign =
                    financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignId(organizationId).orElseThrow(() -> new RuleException("fin.financialPeriod.financialPeriodTypeAssignIdAndOrgan"));
            financialPeriodDto.setFinancialPeriodTypeAssignId(financialPeriodTypeAssign.getId());
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches("1[0-2]|[1-9]")) {
            throw new RuleException("fin.financialPeriod.financialMonth.update");
        }
        if (financialPeriodDto.getStartDate().isAfter(financialPeriodDto.getEndDate())) {
            throw new RuleException("fin.financialPeriod.validationSave.comparisonStartDateAndEndDate");
        }
        Long financialCount = financialPeriodRepository.getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(financialPeriodDto.getStartDate(), financialPeriodDto.getEndDate(), financialPeriodDto.getFinancialPeriodTypeAssignId());
        if (financialCount > 0) {
            throw new RuleException("fin.financialPeriod.validationSave.saveStartDateAndEndDate");
        }
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialPeriodDto changeStatusFinancialPeriodById(FinancialPeriodDto financialPeriodDto) {
        FinancialPeriod financialPeriod = financialPeriodRepository.findById(financialPeriodDto.getId()).orElseThrow(() -> new RuleException("fin.financialPeriod.notFound"));
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
                throw new RuleException("fin.financialPeriod.validationBeforeChangeStatus");
            }
        } else if (financialPeriodDto.getStatusId() == 1) {
            Long exitClose = financialPeriodRepository.checkFinancialStatusIdIsClose(financialPeriodId, organizationId);
            if (exitClose != null && exitClose == 1) {
                throw new RuleException("fin.financialPeriod.validationBeforeChangeStatusClose");
            }
        }

    }

    @Override
    @Transactional
    public FinancialPeriodDateDto getStartDateFinancialPeriod(Long organizationId) {

        List<Object[]> objects = financialPeriodTypeAssignRepository.getStartDateAndEndDate(organizationId);
        if (objects.isEmpty()) {
            throw new RuleException("fin.financialPeriodType.getDate");
        }
        return objectToDto(objects);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest
                                                                                   financialPeriodRequest, Long organizationId) {
        List<Object[]> financialPeriodListObject = financialPeriodRepository.findByFinancialPeriodAndDate(financialPeriodRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), organizationId);
        return financialPeriodListObject.stream().map(objects -> FinancialPeriodResponse.builder().id(Long.parseLong(objects[0].toString()))
                .description(objects[2] == null ? null : objects[2].toString())
                .code(objects[3] == null ? null : objects[3].toString())
                .fullDescription(objects[1].toString())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FinancialPeriodNewResponse getGetPeriodStartDateByOrganizationId(Long organizationId) {
        LocalDateTime countPeriod = financialPeriodRepository.findByFinancialPeriodAndOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        if (countPeriod == null) {
            throw new RuleException("fin.financialPeriodType.getPeriodStartDate");
        }
        FinancialPeriodNewResponse financialPeriodNewResponse = new FinancialPeriodNewResponse();
        financialPeriodNewResponse.setStartDate(countPeriod);
        return financialPeriodNewResponse;
    }

    @Override
    @Transactional
    public FinancialPeriodStatusResponse getFinancialPeriodStatus(FinancialPeriodStatusRequest financialPeriodStatusRequest) {
        if (financialPeriodStatusRequest.getFinancialDocumentId() == null && financialPeriodStatusRequest.getFinancialPeriodId() == null
                && financialPeriodStatusRequest.getDate() == null && financialPeriodStatusRequest.getOrganizationId() == null) {
            throw new RuleException("fin.financialPeriod.getStatus");
        }
        FinancialPeriodStatusResponse financialPeriodStatusResponses = new FinancialPeriodStatusResponse();
        if (financialPeriodStatusRequest.getFinancialDocumentId() != null) {
            List<Object[]> financialDocument = financialDocumentRepository.financialDocumentById(financialPeriodStatusRequest.getFinancialDocumentId());
            financialPeriodStatusRequest.setDate(financialDocument.get(0)[0] == null ? financialPeriodStatusRequest.getDate() : (LocalDateTime) financialDocument.get(0)[0]);
            financialPeriodStatusRequest.setFinancialPeriodId(financialDocument.get(0)[1] == null ? financialPeriodStatusRequest.getFinancialPeriodId() : Long.parseLong(financialDocument.get(0)[1].toString()));
            if (financialPeriodStatusRequest.getFinancialPeriodId() == null || financialPeriodStatusRequest.getDate() == null) {
                throw new RuleException("fin.financialPeriod.list");
            }
        } else if (financialPeriodStatusRequest.getFinancialPeriodId() == null && financialPeriodStatusRequest.getDate() != null && financialPeriodStatusRequest.getOrganizationId() != null) {
            FinancialPeriodRequest financialPeriodRequest = new FinancialPeriodRequest();
            financialPeriodRequest.setDate(financialPeriodStatusRequest.getDate());
            List<FinancialPeriodResponse> accountByDateAndOrgan = this.getFinancialAccountByDateAndOrgan(financialPeriodRequest, SecurityHelper.getCurrentUser().getOrganizationId());
            Long financialPeriodId = null;
            if (!accountByDateAndOrgan.isEmpty()) {
                financialPeriodId = accountByDateAndOrgan.get(0).getId();
            }
            financialPeriodStatusRequest.setFinancialPeriodId(financialPeriodId);
            if (financialPeriodStatusRequest.getFinancialPeriodId() == null) {
                financialPeriodStatusResponses.setMonthStatus(0L);
                financialPeriodStatusResponses.setPeriodStatus(0L);
                return financialPeriodStatusResponses;
            }
        }
        Long periodStatus = financialPeriodRepository.findFinancialPeriodById(financialPeriodStatusRequest.getFinancialPeriodId());
        Long monthStatus = financialPeriodRepository.findFinancialPeriodByFinancialPeriodIdAndDate(financialPeriodStatusRequest.getFinancialPeriodId(), financialPeriodStatusRequest.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        financialPeriodStatusResponses.setPeriodStatus(periodStatus);
        financialPeriodStatusResponses.setMonthStatus(monthStatus);
        return financialPeriodStatusResponses;
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


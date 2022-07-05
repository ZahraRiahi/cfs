package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialPeriodGetDateRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDateDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodNewResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.model.entity.FinancialMonth;
import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cfs.model.entity.FinancialPeriodParameter;
import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialMonthRepository;
import ir.demisco.cfs.service.repository.FinancialMonthStatusRepository;
import ir.demisco.cfs.service.repository.FinancialMonthTypeRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodParameterRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeAssignRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeRepository;
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
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {

    private final GridFilterService gridFilterService;
    private final FinancialPeriodStatusRepository financialPeriodStatusRepository;
    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialMonthTypeRepository financialMonthTypeRepository;
    private final FinancialMonthRepository financialMonthRepository;
    private final FinancialMonthStatusRepository financialMonthStatusRepository;
    private final FinancialPeriodParameterRepository periodParameterRepository;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialPeriodTypeRepository financialPeriodTypeRepository;
    private final FinancialPeriodTypeAssignListGridProvider financialPeriodTypeAssignListGridProvider;
    private static final Pattern myRegex = Pattern.compile("1[0-2]|[1-9]");


    public DefaultFinancialPeriod(GridFilterService gridFilterService, FinancialPeriodStatusRepository financialPeriodStatusRepository, FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository, FinancialPeriodRepository financialPeriodRepository, FinancialMonthTypeRepository financialMonthTypeRepository, FinancialMonthRepository financialMonthRepository, FinancialMonthStatusRepository financialMonthStatusRepository, FinancialPeriodParameterRepository periodParameterRepository, FinancialDocumentRepository financialDocumentRepository, FinancialPeriodTypeRepository financialPeriodTypeRepository, FinancialPeriodTypeAssignListGridProvider financialPeriodTypeAssignListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodStatusRepository = financialPeriodStatusRepository;
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialMonthTypeRepository = financialMonthTypeRepository;
        this.financialMonthRepository = financialMonthRepository;
        this.financialMonthStatusRepository = financialMonthStatusRepository;
        this.periodParameterRepository = periodParameterRepository;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialPeriodTypeRepository = financialPeriodTypeRepository;
        this.financialPeriodTypeAssignListGridProvider = financialPeriodTypeAssignListGridProvider;
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
                .FilterDescriptor.create("organization.id", organizationId, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor.create("activeFlag", 1, DataSourceRequest.Operators.EQUALS));
        return gridFilterService.filter(dataSourceRequest, financialPeriodTypeAssignListGridProvider);
    }
    private String getItemForString(Object[] item, int i) {
        return item[i] == null ? null : item[i].toString();
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

        financialPeriod.setCode(financialPeriodRepository.getCodeFinancialPeriod(organizationId));
        financialPeriod.setDescription(financialPeriodRepository.getDescriptionFinancialPeriod(financialPeriodDto.getEndDate().toString().split("T")[0]));
        financialPeriod.setFinancialPeriodType(financialPeriodTypeRepository.getOne(financialPeriodDto.getFinancialPeriodTypeId()));
        financialPeriod = financialPeriodRepository.save(financialPeriod);
        List<Object[]> list = financialMonthTypeRepository.findByParam(organizationId, financialPeriod.getId());
        FinancialPeriod finalFinancialPeriod = financialPeriod;
        list.forEach((Object[] item) -> {
            FinancialMonth financialMonth = new FinancialMonth();
            financialMonth.setFinancialPeriod(finalFinancialPeriod);
            financialMonth.setFinancialMonthType(financialMonthTypeRepository.findById(Long.parseLong(item[0].toString())).orElse(null));
            financialMonth.setFinancialMonthStatus(financialMonthStatusRepository.findById(1L).orElse(null));
            financialMonth.setStartDate(DateUtil.convertStringToDate(item[1].toString()));
            financialMonth.setEndDate(DateUtil.convertStringToDate(item[2].toString()));
            financialMonth.setDescription(getItemForString(item, 3));
            financialMonthRepository.save(financialMonth);
        });
        List<Object[]> periodParameters = periodParameterRepository.getPeriodParameterByPeriodId(organizationId, finalFinancialPeriod.getId());
        periodParameters.forEach((Object [] objects) -> {
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
            } else if (flag == 0) {
                financialPeriodParameter.setVatFillFlag(false);
            } else {
                throw new RuleException("fin.allMessage");
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
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches(myRegex.pattern())) {
            throw new RuleException("fin.financialPeriod.financialMonth.update");
        }
        if (financialPeriod.getStartDate() != null && !financialPeriod.getStartDate().equals(financialPeriodDto.getStartDate()) && mode.equals("end")) {
            throw new RuleException("fin.financialPeriod.financialMonthStartDate.update");
        }
        if (financialPeriodDto.getId() == null && mode.equals("start")) {
            throw new RuleException("fin.financialPeriod.update");
        }
        List<FinancialPeriod> period = financialPeriodTypeAssignRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId, "OPEN", financialPeriodDto.getFinancialPeriodTypeId());
        if (period.size() >= 3 && mode.equals("end")) {
            throw new RuleException("fin.financialPeriod.validationUpdate");
        }
        if (period.size() > 2 && mode.equals("change")) {
            throw new RuleException("fin.financialPeriod.validationUpdate");
        }
    }

    private void validationSave(FinancialPeriodDto financialPeriodDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        List<FinancialPeriod> period = financialPeriodTypeAssignRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId, "OPEN", financialPeriodDto.getFinancialPeriodTypeId());
        if (period.size() >= 2) {
            throw new RuleException("fin.financialPeriod.validationSave");
        }
            {
            FinancialPeriodTypeAssign financialPeriodTypeAssign =
                    financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignId(organizationId).orElseThrow(() -> new RuleException("fin.financialPeriod.financialPeriodTypeAssignIdAndOrgan"));
            financialPeriodDto.setFinancialPeriodTypeAssignId(financialPeriodTypeAssign.getId());
        }
        if (!String.valueOf(financialPeriodDto.getOpenMonthCount()).matches(myRegex.pattern())) {
            throw new RuleException("fin.financialPeriod.financialMonth.update");
        }
        if (financialPeriodDto.getStartDate().isAfter(financialPeriodDto.getEndDate())) {
            throw new RuleException("fin.financialPeriod.validationSave.comparisonStartDateAndEndDate");
        }
        Long financialCount = financialPeriodTypeAssignRepository.getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(financialPeriodDto.getStartDate(), financialPeriodDto.getEndDate(), financialPeriodDto.getFinancialPeriodTypeAssignId(), financialPeriodDto.getFinancialPeriodTypeId());
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
            Long existOpen =financialPeriodTypeAssignRepository.checkFinancialStatusIdIsOpen(financialPeriodId, organizationId);
            if (existOpen != null && existOpen == 1) {
                throw new RuleException("fin.financialPeriod.validationBeforeChangeStatus");
            }
        }
        if (financialPeriodDto.getStatusId() == 1) {
            Long exitClose = financialPeriodTypeAssignRepository.checkFinancialStatusIdIsClose(financialPeriodId, organizationId);
            if (exitClose != null && exitClose == 1) {
                throw new RuleException("fin.financialPeriod.validationBeforeChangeStatusClose");
            }
        }

    }

    @Override
    @Transactional
    public FinancialPeriodDateDto getStartDateFinancialPeriod(Long organizationId, FinancialPeriodGetDateRequest financialPeriodGetDateRequest) {
        Object financialPeriodType = null;
        if (financialPeriodGetDateRequest.getFinancialPeriodTypeId() != null) {
            financialPeriodType = "financialPeriodType";
        } else {
            financialPeriodGetDateRequest.setFinancialPeriodTypeId(0L);
        }
        List<Object[]> objects = financialPeriodTypeAssignRepository.getStartDateAndEndDate(SecurityHelper.getCurrentUser().getOrganizationId(), financialPeriodType, financialPeriodGetDateRequest.getFinancialPeriodTypeId());
        if (objects.isEmpty()) {
            throw new RuleException("fin.financialPeriodType.getDate");
        }
        return objectToDto(objects);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest
                                                                                   financialPeriodRequest, Long organizationId) {
        Object financialPeriodType = null;
        if (financialPeriodRequest.getFinancialPeriodTypeId() != null) {
            financialPeriodType = "financialPeriodType";
        } else {
            financialPeriodRequest.setFinancialPeriodTypeId(0L);
        }
        List<Object[]> financialPeriodListObject = financialPeriodRepository.findByFinancialPeriodAndDate(financialPeriodRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), SecurityHelper.getCurrentUser().getOrganizationId(), financialPeriodType, financialPeriodRequest.getFinancialPeriodTypeId());
        return financialPeriodListObject.stream().map(objects -> FinancialPeriodResponse.builder().id(Long.parseLong(objects[0].toString()))
                .description(objects[2] == null ? null : objects[2].toString())
                .code(objects[3] == null ? null : objects[3].toString())
                .fullDescription(objects[1].toString())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FinancialPeriodNewResponse getGetPeriodStartDateByOrganizationId(Long organizationId, FinancialPeriodGetDateRequest financialPeriodGetDateRequest) {
        Object financialPeriodType = null;
        if (financialPeriodGetDateRequest.getFinancialPeriodTypeId() != null) {
            financialPeriodType = "financialPeriodType";
        } else {
            financialPeriodGetDateRequest.setFinancialPeriodTypeId(0L);
        }
        LocalDateTime countPeriod = financialPeriodRepository.findByFinancialPeriodAndOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId(), financialPeriodType, financialPeriodGetDateRequest.getFinancialPeriodTypeId());
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
        checkFinancialPeriodStatus(financialPeriodStatusRequest);
        FinancialPeriodStatusResponse financialPeriodStatusResponses = new FinancialPeriodStatusResponse();
        if (financialPeriodStatusRequest.getFinancialDocumentId() != null) {
            List<Object[]> financialDocument = financialDocumentRepository.financialDocumentById(financialPeriodStatusRequest.getFinancialDocumentId());
            financialPeriodStatusRequest.setDate(financialDocument.get(0)[1] == null ? financialPeriodStatusRequest.getDate() : LocalDateTime.parse(financialDocument.get(0)[1].toString().substring(0, 10) + "T00:00"));
            financialPeriodStatusRequest.setFinancialPeriodId(financialDocument.get(0)[0] == null ? financialPeriodStatusRequest.getFinancialPeriodId() : Long.parseLong(financialDocument.get(0)[0].toString()));
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
        } else {
            throw new RuleException("fin.allMessage");
        }
        Long periodStatus = financialPeriodRepository.findFinancialPeriodById(financialPeriodStatusRequest.getFinancialPeriodId());
//        Long monthStatus = financialPeriodRepository.findFinancialPeriodByFinancialPeriodIdAndDate(financialPeriodStatusRequest.getFinancialPeriodId(), financialPeriodStatusRequest.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        financialPeriodStatusResponses.setPeriodStatus(periodStatus);
        financialPeriodStatusResponses.setMonthStatus(1L);
        return financialPeriodStatusResponses;
    }

    private void checkFinancialPeriodStatus(FinancialPeriodStatusRequest financialPeriodStatusRequest) {
        if (financialPeriodStatusRequest.getFinancialDocumentId() == null && financialPeriodStatusRequest.getFinancialPeriodId() == null
                && financialPeriodStatusRequest.getDate() == null && financialPeriodStatusRequest.getOrganizationId() == null) {
            throw new RuleException("fin.financialPeriod.getStatus");
        }
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
                .financialPeriodTypeId(financialPeriod.getFinancialPeriodType() == null ? 0 : financialPeriod.getFinancialPeriodType().getId())
                .build();
    }
}


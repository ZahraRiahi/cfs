package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialMonthDto;
import ir.demisco.cfs.model.entity.FinancialMonth;
import ir.demisco.cfs.model.entity.FinancialPeriodStatus;
import ir.demisco.cfs.service.api.FinancialMonthService;
import ir.demisco.cfs.service.repository.FinancialMonthRepository;
import ir.demisco.cfs.service.repository.FinancialMonthStatusRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialMonth implements FinancialMonthService {
    private final GridFilterService gridFilterService;
    private final FinancialMonthListGridProvider financialMonthListGridProvider;
    private final FinancialMonthRepository financialMonthRepository;
    private final FinancialMonthStatusRepository financialMonthStatusRepository;

    public DefaultFinancialMonth(GridFilterService gridFilterService, FinancialMonthListGridProvider financialMonthListGridProvider, FinancialMonthRepository financialMonthRepository, FinancialMonthStatusRepository financialMonthStatusRepository) {
        this.gridFilterService = gridFilterService;
        this.financialMonthListGridProvider = financialMonthListGridProvider;
        this.financialMonthRepository = financialMonthRepository;
        this.financialMonthStatusRepository = financialMonthStatusRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialMonthByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(financialPeriodId, "financialPeriodId is null");
        dataSourceRequest.setFilter(DataSourceRequest.FilterDescriptor.create("financialPeriod.id", financialPeriodId));
        return gridFilterService.filter(dataSourceRequest, financialMonthListGridProvider);
    }

    @Override
    @Transactional
    public FinancialMonthDto changeStatusFinancialMonthById(Long financialMonthId) {
        FinancialMonth financialMonth = financialMonthRepository.findById(financialMonthId).orElseThrow(() -> new RuleException("هیچ ماه عملیاتی یافت نشد."));
        if (financialMonth.getFinancialMonthStatus().getId().equals(1L)) {
            financialMonth.setFinancialMonthStatus(financialMonthStatusRepository.getOne(2L));
        } else {
            String financialPeriodStatusCode = financialMonth.getFinancialPeriod().getFinancialPeriodStatus().getCode();
            if (financialPeriodStatusCode.equals(FinancialPeriodStatus.Code.CLOSE.getCode())) {
                throw new RuleException("به دلیل بسته بودن وضعیت دوره ی مالی امکان باز شدن وضعیت ماههای عملیاتی وجود ندارد.");
            }
            financialMonth.setFinancialMonthStatus(financialMonthStatusRepository.getOne(1L));
        }
        financialMonthRepository.save(financialMonth);
        return convertFinancialMonthToDto(financialMonth);
    }

    private FinancialMonthDto convertFinancialMonthToDto(FinancialMonth financialMonth) {
        return FinancialMonthDto.builder()
                .financialPeriodId(financialMonth.getFinancialPeriod().getId())
                .financialMonthTypeId(financialMonth.getFinancialMonthType().getId())
                .financialMonthTypeDescription(financialMonth.getFinancialMonthType().getDescription())
                .financialMonthStatusId(financialMonth.getFinancialMonthStatus().getId())
                .financialMonthStatusName(financialMonth.getFinancialMonthStatus().getName())
                .build();
    }
}

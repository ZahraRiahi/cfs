package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialMonthService;
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

    public DefaultFinancialMonth(GridFilterService gridFilterService, FinancialMonthListGridProvider financialMonthListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialMonthListGridProvider = financialMonthListGridProvider;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialMonthByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(financialPeriodId, "financialPeriodId is null");
        dataSourceRequest.setFilter(DataSourceRequest.FilterDescriptor.create("financialPeriod.id", financialPeriodId));
        return gridFilterService.filter(dataSourceRequest, financialMonthListGridProvider);
    }
}

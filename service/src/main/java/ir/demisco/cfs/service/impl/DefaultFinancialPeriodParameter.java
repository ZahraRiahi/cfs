package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialPeriodParameterService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialPeriodParameter implements FinancialPeriodParameterService {
    private final GridFilterService gridFilterService;
    private final FinancialPeriodParameterListGridProvider financialPeriodParameterListGridProvider;

    public DefaultFinancialPeriodParameter(GridFilterService gridFilterService, FinancialPeriodParameterListGridProvider financialPeriodParameterListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodParameterListGridProvider = financialPeriodParameterListGridProvider;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodParameterByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(financialPeriodId, "financialPeriodId is null");
        dataSourceRequest.setFilter(DataSourceRequest.FilterDescriptor.create("financialPeriod.id", financialPeriodId));
        return gridFilterService.filter(dataSourceRequest, financialPeriodParameterListGridProvider);
    }
}

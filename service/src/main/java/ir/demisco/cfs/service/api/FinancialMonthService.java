package ir.demisco.cfs.service.api;

import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialMonthService {
    DataSourceResult getFinancialMonthByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest);

}

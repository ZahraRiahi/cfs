package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialMonthDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialMonthService {
    DataSourceResult getFinancialMonthByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest);

    FinancialMonthDto changeStatusFinancialMonthById(Long financialMonthId);
}

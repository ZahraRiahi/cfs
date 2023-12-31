package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialPeriodParameterDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;


public interface FinancialPeriodParameterService {
    DataSourceResult getFinancialPeriodParameterByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest);

    Long save(FinancialPeriodParameterDto financialPeriodParameterDto);
}

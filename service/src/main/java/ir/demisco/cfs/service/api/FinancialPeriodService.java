package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

import java.time.LocalDateTime;


public interface FinancialPeriodService {

    DataSourceResult getFinancialPeriodByOrganizationId(Long OrganizationId, DataSourceRequest dataSourceRequest);

    Long save(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDto update(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDto changeStatusFinancialPeriodById(FinancialPeriodDto financialPeriodDto);

    LocalDateTime getStartDateFinancialPeriod(Long organizationId);
}

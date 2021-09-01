package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDateDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

import java.util.List;


public interface FinancialPeriodService {

    DataSourceResult getFinancialPeriodByOrganizationId(Long OrganizationId, DataSourceRequest dataSourceRequest);

    Long save(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDto update(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDto changeStatusFinancialPeriodById(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDateDto getStartDateFinancialPeriod(Long organizationId);

    List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest financialPeriodRequest, Long organizationId);

}

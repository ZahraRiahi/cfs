package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialPeriodGetDateRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface FinancialPeriodService {

    DataSourceResult getFinancialPeriodByOrganizationId(Long OrganizationId, DataSourceRequest dataSourceRequest);

    FinancialPeriodDto save(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDto update(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDto changeStatusFinancialPeriodById(FinancialPeriodDto financialPeriodDto);

    FinancialPeriodDateDto getStartDateFinancialPeriod(Long organizationId ,FinancialPeriodGetDateRequest financialPeriodGetDateRequest);

    List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest financialPeriodRequest, Long organizationId);

    FinancialPeriodNewResponse getGetPeriodStartDateByOrganizationId(Long organizationId, FinancialPeriodGetDateRequest financialPeriodGetDateRequest);

    FinancialPeriodStatusResponse getFinancialPeriodStatus(FinancialPeriodStatusRequest financialPeriodStatusRequest);
}

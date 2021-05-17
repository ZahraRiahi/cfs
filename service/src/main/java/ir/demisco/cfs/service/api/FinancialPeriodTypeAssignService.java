package ir.demisco.cfs.service.api;


import ir.demisco.cfs.model.dto.response.FinancialPeriodParameterDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignDto;

import java.util.List;

public interface FinancialPeriodTypeAssignService {

    List<FinancialPeriodTypeAssignDto> getFinancialPeriodTypeAssignByOrganizationId(Long OrganizationId);

    Long save(FinancialPeriodTypeAssignDto financialPeriodTypeAssignDto);

}

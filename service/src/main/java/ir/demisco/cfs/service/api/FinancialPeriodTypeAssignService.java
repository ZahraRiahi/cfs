package ir.demisco.cfs.service.api;


import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignSaveDto;

import java.util.List;

public interface FinancialPeriodTypeAssignService {

    List<FinancialPeriodTypeAssignDto> getFinancialPeriodTypeAssignByOrganizationId(Long organizationId);

    FinancialPeriodTypeAssignSaveDto save(FinancialPeriodTypeAssignDto financialPeriodTypeAssignDto);

}

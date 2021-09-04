package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeResponse;

import java.util.List;

public interface FinancialPeriodTypeService {
    List<FinancialPeriodTypeResponse> getFinancialPeriodType();

}

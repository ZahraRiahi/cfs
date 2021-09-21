package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeResponse;
import ir.demisco.cfs.model.entity.FinancialPeriodType;
import ir.demisco.cfs.service.api.FinancialPeriodTypeService;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialPeriodType implements FinancialPeriodTypeService {
    private final FinancialPeriodTypeRepository financialPeriodTypeRepository;

    public DefaultFinancialPeriodType(FinancialPeriodTypeRepository financialPeriodTypeRepository) {
        this.financialPeriodTypeRepository = financialPeriodTypeRepository;
    }

    @Override
    @Transactional
    public List<FinancialPeriodTypeResponse> getFinancialPeriodType() {
        List<FinancialPeriodType> financialPeriodTypeList = financialPeriodTypeRepository.findByFinancialPeriodType();
        return financialPeriodTypeList.stream().map(e -> FinancialPeriodTypeResponse.builder().id(e.getId())
                .description(e.getDescription())
                .build()).collect(Collectors.toList());
    }

}

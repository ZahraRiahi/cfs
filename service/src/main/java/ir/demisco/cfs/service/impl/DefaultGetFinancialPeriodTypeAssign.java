package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignDto;
import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import ir.demisco.cfs.service.api.FinancialPeriodTypeAssignService;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeAssignRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultGetFinancialPeriodTypeAssign implements FinancialPeriodTypeAssignService {
    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;

    public DefaultGetFinancialPeriodTypeAssign(FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository) {
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
    }

    @Override
    @Transactional
    public List<FinancialPeriodTypeAssignDto> getFinancialPeriodTypeAssignByOrganizationId(Long OrganizationId) {
        List<FinancialPeriodTypeAssign> financialPeriodTypeList = financialPeriodTypeAssignRepository.findByOrganizationId(OrganizationId);

        return financialPeriodTypeList.stream().map(e -> FinancialPeriodTypeAssignDto.builder().id(e.getId())
                .financialPeriodTypeDescription(e.getFinancialPeriodType().getDescription())
                .financialPeriodTypeId(e.getFinancialPeriodType().getId()).organizationId(e.getOrganization().getId()).build()).collect(Collectors.toList());
    }
}

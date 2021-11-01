package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialPeriodTypeAssignRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignSaveDto;
import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import ir.demisco.cfs.service.api.FinancialPeriodTypeAssignService;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeAssignRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultGetFinancialPeriodTypeAssign implements FinancialPeriodTypeAssignService {
    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialPeriodTypeRepository financialPeriodTypeRepository;

    public DefaultGetFinancialPeriodTypeAssign(FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository, OrganizationRepository organizationRepository, FinancialPeriodTypeRepository financialPeriodTypeRepository) {
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
        this.organizationRepository = organizationRepository;
        this.financialPeriodTypeRepository = financialPeriodTypeRepository;
    }

    @Override
    @Transactional
    public List<FinancialPeriodTypeAssignDto> getFinancialPeriodTypeAssignByOrganizationId(Long organizationId) {
        List<FinancialPeriodTypeAssign> financialPeriodTypeList = financialPeriodTypeAssignRepository.findByOrganizationId(organizationId);

        return financialPeriodTypeList.stream().map(e -> FinancialPeriodTypeAssignDto.builder().id(e.getId())
                .financialPeriodTypeDescription(e.getFinancialPeriodType().getDescription())
                .financialPeriodTypeId(e.getFinancialPeriodType().getId())
                .startDate(e.getStartDate())
                .activeFlag(e.getActiveFlag())
                .organizationId(e.getOrganization().getId()).build()).collect(Collectors.toList());
    }

    @Override
    public FinancialPeriodTypeAssignSaveDto save(FinancialPeriodTypeAssignRequest financialPeriodTypeAssignRequest) {
        Long organizationId = 100L;
        FinancialPeriodTypeAssign financialPeriodTypeAssign = financialPeriodTypeAssignRepository.findById(financialPeriodTypeAssignRequest.getId() == null ? 0L : financialPeriodTypeAssignRequest.getId()).orElse(new FinancialPeriodTypeAssign());
        Long periodTypeAssign = financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignAndOrganAndPeriodTypeAndStartDate(100L, financialPeriodTypeAssignRequest.getFinancialPeriodTypeId());
        if (periodTypeAssign != null) {
            throw new RuleException("اطلاعات تکراری است");
        } else {
            financialPeriodTypeAssignRepository.getFinancialPeriodTypeAssignId(organizationId).ifPresent(financialPeriodTypeAssign1 -> financialPeriodTypeAssign1.setActiveFlag(0L));

        }
        financialPeriodTypeAssign.setOrganization(organizationRepository.getOne(organizationId));
        financialPeriodTypeAssign.setFinancialPeriodType(financialPeriodTypeRepository.getOne(financialPeriodTypeAssignRequest.getFinancialPeriodTypeId()));
        financialPeriodTypeAssign.setActiveFlag(1L);
        financialPeriodTypeAssign.setStartDate(financialPeriodTypeAssignRequest.getStartDate());
        financialPeriodTypeAssign = financialPeriodTypeAssignRepository.save(financialPeriodTypeAssign);
        return convertFinancialPeriodTypeAssignToDto(financialPeriodTypeAssign);
    }

    private FinancialPeriodTypeAssignSaveDto convertFinancialPeriodTypeAssignToDto(FinancialPeriodTypeAssign financialPeriodTypeAssign) {
        return FinancialPeriodTypeAssignSaveDto.builder()
                .id(financialPeriodTypeAssign.getId())
                .financialPeriodTypeId(financialPeriodTypeAssign.getFinancialPeriodType().getId())
                .organizationId(financialPeriodTypeAssign.getOrganization().getId())
                .activeFlag(financialPeriodTypeAssign.getActiveFlag())
                .startDate(financialPeriodTypeAssign.getStartDate())
                .build();
    }

}

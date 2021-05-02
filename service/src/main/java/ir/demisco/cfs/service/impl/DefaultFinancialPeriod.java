package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {


    private final GridFilterService gridFilterService;
    private final FinancialPeriodListGridProvider financialPeriodListGridProvider;

    public DefaultFinancialPeriod( GridFilterService gridFilterService, FinancialPeriodListGridProvider financialPeriodListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodListGridProvider = financialPeriodListGridProvider;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodByOrganizationId(Long organizationId,DataSourceRequest dataSourceRequest) {
//        dataSourceRequest.getFilter().addChildFilterDescriptor(DataSourceRequest.FilterDescriptor.create("financialPeriodTypeAssign.organization.id",organizationId));

        return gridFilterService.filter(dataSourceRequest, financialPeriodListGridProvider);


//        List<FinancialPeriod> financialPeriodList = financialPeriodRepository.findByFinancialPeriodTypeAssignOrganizationId(organizationId);
//        return financialPeriodList
//                .stream()
//                .map(financialPeriod -> FinancialPeriodDto.builder()
//                        .id(financialPeriod.getId())
//                        .startDate(financialPeriod.getStartDate())
//                        .endDate(financialPeriod.getEndDate())
//                        .openMonthCount(financialPeriod.getOpenMonthCount())
//                        .financialPeriodStatusDto(FinancialPeriodStatusDto.builder()
//                                .ID(financialPeriod.getFinancialPeriodStatus().getId())
//                                .Description(financialPeriod.getFinancialPeriodStatus().getDescription())
//                                .build())
//                        .financialPeriodTypeAssignId(financialPeriod.getFinancialPeriodTypeAssign().getId())
//                        .build())
//                .collect(Collectors.toList());
    }
}

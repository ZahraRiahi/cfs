package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodTypeAssignRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {


    private final GridFilterService gridFilterService;
    private final FinancialPeriodListGridProvider financialPeriodListGridProvider;
    private final FinancialPeriodStatusRepository financialPeriodStatusRepository;
    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;
    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultFinancialPeriod(GridFilterService gridFilterService, FinancialPeriodListGridProvider financialPeriodListGridProvider, FinancialPeriodStatusRepository financialPeriodStatusRepository, FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository, FinancialPeriodRepository financialPeriodRepository) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodListGridProvider = financialPeriodListGridProvider;
        this.financialPeriodStatusRepository = financialPeriodStatusRepository;
        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {
        dataSourceRequest.setFilter(DataSourceRequest.FilterDescriptor.create("financialPeriodTypeAssign.organization.id",organizationId));

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
//
@Override
public void save(FinancialPeriodDto financialPeriodDto) {
    FinancialPeriod financialPeriod = new FinancialPeriod();
    financialPeriod.setEndDate(financialPeriodDto.getEndDate());
    financialPeriod.setStartDate(financialPeriodDto.getStartDate());
    financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
    financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(financialPeriodDto.getFinancialPeriodStatus().getId()));
    financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssignRepository.getOne(financialPeriodDto.getFinancialPeriodTypeAssignId()));
    financialPeriodRepository.save(financialPeriod);
}

}

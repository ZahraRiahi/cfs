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
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class DefaultFinancialPeriod implements FinancialPeriodService {


    private final GridFilterService gridFilterService;
    private final FinancialPeriodListGridProvider financialPeriodListGridProvider;
//    private final FinancialPeriodStatusRepository financialPeriodStatusRepository;
//    private final FinancialPeriodTypeAssignRepository financialPeriodTypeAssignRepository;
//    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultFinancialPeriod(GridFilterService gridFilterService, FinancialPeriodListGridProvider financialPeriodListGridProvider) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodListGridProvider = financialPeriodListGridProvider;
//        this.financialPeriodStatusRepository = financialPeriodStatusRepository;
//        this.financialPeriodTypeAssignRepository = financialPeriodTypeAssignRepository;
//        this.financialPeriodRepository = financialPeriodRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(organizationId,"organizationId is null");
        dataSourceRequest.setFilter(DataSourceRequest.FilterDescriptor.create("financialPeriodTypeAssign.organization.id",organizationId));
        return gridFilterService.filter(dataSourceRequest, financialPeriodListGridProvider);
    }

//@Override
//public void save(FinancialPeriodDto financialPeriodDto) {
//    FinancialPeriod financialPeriod = new FinancialPeriod();
//    financialPeriod.setEndDate(financialPeriodDto.getEndDate());
//    financialPeriod.setStartDate(financialPeriodDto.getStartDate());
//    financialPeriod.setOpenMonthCount(financialPeriodDto.getOpenMonthCount());
//    financialPeriod.setFinancialPeriodStatus(financialPeriodStatusRepository.getOne(financialPeriodDto.getFinancialPeriodStatus().getId()));
//    financialPeriod.setFinancialPeriodTypeAssign(financialPeriodTypeAssignRepository.getOne(financialPeriodDto.getFinancialPeriodTypeAssignId()));
//    financialPeriodRepository.save(financialPeriod);
//}

}

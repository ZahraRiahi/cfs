package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodParameterDto;
import ir.demisco.cfs.model.entity.FinancialPeriodParameter;
import ir.demisco.cfs.service.api.FinancialPeriodParameterService;
import ir.demisco.cfs.service.repository.FinancialPeriodParameterRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.middle.service.business.api.core.GridFilterService;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DefaultFinancialPeriodParameter implements FinancialPeriodParameterService {
    private final GridFilterService gridFilterService;
    private final FinancialPeriodParameterListGridProvider financialPeriodParameterListGridProvider;
    private final FinancialPeriodParameterRepository financialPeriodParameterRepository;
    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultFinancialPeriodParameter(GridFilterService gridFilterService, FinancialPeriodParameterListGridProvider financialPeriodParameterListGridProvider, FinancialPeriodParameterRepository financialPeriodParameterRepository, FinancialPeriodRepository financialPeriodRepository) {
        this.gridFilterService = gridFilterService;
        this.financialPeriodParameterListGridProvider = financialPeriodParameterListGridProvider;
        this.financialPeriodParameterRepository = financialPeriodParameterRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialPeriodParameterByFinancialPeriodId(Long financialPeriodId, DataSourceRequest dataSourceRequest) {
        Asserts.notNull(financialPeriodId, "financialPeriodId is null");
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest
                .FilterDescriptor.create("financialPeriod.id", financialPeriodId,DataSourceRequest.Operators.EQUALS));
        return gridFilterService.filter(dataSourceRequest, financialPeriodParameterListGridProvider);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Long save(FinancialPeriodParameterDto financialPeriodParameterDto) {
        FinancialPeriodParameter financialPeriodParameter = financialPeriodParameterRepository.findById(financialPeriodParameterDto.getId() == null ? 0L : financialPeriodParameterDto.getId()).orElse(new FinancialPeriodParameter());
        financialPeriodParameter.setFinancialPeriod(financialPeriodRepository.getOne(financialPeriodParameterDto.getFinancialPeriodId()));
        financialPeriodParameter.setStartDate(financialPeriodParameterDto.getStartDate());
        financialPeriodParameter.setTaxDeductionRate(financialPeriodParameterDto.getTaxDeductionRate());
        financialPeriodParameter.setVatTaxRate(financialPeriodParameterDto.getVatTaxRate());
        financialPeriodParameter.setVatTollRate(financialPeriodParameterDto.getVatTollRate());
        financialPeriodParameter.setInsuranceDeductionRate(financialPeriodParameterDto.getInsuranceDeductionRate());
        financialPeriodParameter.setMaxFewerAmount(financialPeriodParameterDto.getMaxFewerAmount());
        financialPeriodParameter.setVatFillFlag(financialPeriodParameterDto.getVatFillFlag());
        return financialPeriodParameterRepository.save(financialPeriodParameter).getId();
    }

}

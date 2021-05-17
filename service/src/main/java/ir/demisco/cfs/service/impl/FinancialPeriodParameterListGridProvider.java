package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodParameterDto;
import ir.demisco.cfs.model.entity.FinancialPeriodParameter;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialPeriodParameterListGridProvider implements GridDataProvider {
    @Override
    public Class<?> getRootEntityClass() {
        return FinancialPeriodParameter.class;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("financialPeriod.id"),
                filterContext.getPath("startDate"),
                filterContext.getPath("taxDeductionRate"),
                filterContext.getPath("vatTaxRate"),
                filterContext.getPath("insuranceDeductionRate"),
                filterContext.getPath("maxFewerAmount"),
                filterContext.getPath("vatFillFlag")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {

        return resultList.stream().map(object -> {
            Object[] array = (Object[]) object;
            return FinancialPeriodParameterDto.builder()
                    .id((Long) array[0])
                    .financialPeriodId((Long) array[1])
                    .startDate((LocalDateTime) array[2])
                    .vatTaxRate((Long) array[3])
                    .vatTollRate((Long) array[4])
                    .insuranceDeductionRate((Long) array[5])
                    .maxFewerAmount((Long) array[6])
                    .vatFillFlag(((Long) array[7]))
                    .build();
        }).collect(Collectors.toList());
    }

}

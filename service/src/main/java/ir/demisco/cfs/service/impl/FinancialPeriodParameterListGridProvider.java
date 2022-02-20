package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodParameterDto;
import ir.demisco.cfs.model.entity.FinancialPeriodParameter;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.Date;
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
                filterContext.getPath("vatTollRate"),
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
                    .startDate((Date) array[2])
                    .taxDeductionRate((Long) array[3])
                    .vatTaxRate((Long) array[4])
                    .vatTollRate((Long) array[5])
                    .insuranceDeductionRate((Long) array[6])
                    .maxFewerAmount((Long) array[7])
                    .vatFillFlag(((Boolean) array[8]))
                    .build();
        }).collect(Collectors.toList());
    }

}

package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialMonthDto;
import ir.demisco.cfs.model.entity.FinancialMonth;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialMonthListGridProvider implements GridDataProvider {

    @Override
    public Class<?> getRootEntityClass() {
        return FinancialMonth.class;
    }

    @Override
    public Predicate getCustomRestriction(FilterContext filterContext) {
        return null;
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("financialPeriod.id"),
                filterContext.getPath("financialMonthStatus.id"),
                filterContext.getPath("financialMonthStatus.name"),
                filterContext.getPath("financialMonthType.id"),
                filterContext.getPath("financialMonthType.description")
        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {

        return resultList.stream().map(object -> {
            Object[] array = (Object[]) object;

            return FinancialMonthDto.builder()
                    .id((Long) array[0])
                    .financialPeriodId((Long) array[1])
                    .financialMonthStatusId((Long) array[2])
                    .financialMonthStatusName((String) array[3])
                    .financialMonthTypeId((Long) array[4])
                    .financialMonthTypeDescription((String) array[5])
                    .build();
        }).collect(Collectors.toList());
    }
}

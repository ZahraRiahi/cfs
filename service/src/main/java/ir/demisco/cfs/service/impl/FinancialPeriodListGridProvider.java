package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.service.business.api.core.GridDataProvider;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialPeriodListGridProvider implements GridDataProvider {

    @Override
    public Class<?> getRootEntityClass() {
        return FinancialPeriod.class;
    }

    @Override
    public List<Order> getCustomSort(FilterContext filterContext) {
        return Collections.singletonList(filterContext.getCriteriaBuilder().desc(filterContext.getPath("startDate")));
    }

    @Override
    public Selection<?> getCustomSelection(FilterContext filterContext) {
        CriteriaBuilder criteriaBuilder = filterContext.getCriteriaBuilder();
        return criteriaBuilder.array(
                filterContext.getPath("id"),
                filterContext.getPath("startDate"),
                filterContext.getPath("endDate"),
                filterContext.getPath("openMonthCount"),
                filterContext.getPath("financialPeriodStatus.id"),
                filterContext.getPath("financialPeriodStatus.name"),
                filterContext.getPath("financialPeriodStatus.code"),
                filterContext.getPath("periodTypeAssignList.id"),
                filterContext.getPath("description"),
                filterContext.getPath("code"),
                filterContext.getPath("financialPeriodType.id")

        );
    }

    @Override
    public List<Object> mapToDto(List<Object> resultList) {

        return resultList.stream().map((Object object) -> {
            Object[] array = (Object[]) object;

            return FinancialPeriodDto.builder()
                    .id((Long) array[0])
                    .startDate((LocalDateTime) array[1])
                    .endDate((LocalDateTime) array[2])
                    .openMonthCount((Long) array[3])
                    .statusId((Long) array[4])
                    .statusName((String) array[5])
                    .statusCode((String) array[6])
                    .financialPeriodTypeAssignId((Long) array[7])
                    .description((String) array[8])
                    .code((String) array[9])
                    .financialPeriodTypeId((Long) array[10])
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public Predicate getCustomRestriction(FilterContext filterContext) {
        DataSourceRequest dataSourceRequest = filterContext.getDataSourceRequest();
        boolean flagSearch = false;
        for (DataSourceRequest.FilterDescriptor filter : dataSourceRequest.getFilter().getFilters()) {
            if ("SEARCH_STATUS_FLAG".equals(filter.getField())) {
                filter.setDisable(true);
                if (filter.getValue() instanceof Integer) {
                    flagSearch = (int) filter.getValue() == 1;
                }
            }

        }
        if (flagSearch) {
            dataSourceRequest.getFilter().getFilters().add(DataSourceRequest
                    .FilterDescriptor.create("financialPeriodStatus.id", 1L, DataSourceRequest.Operators.EQUALS));
        }
        return null;
    }
}

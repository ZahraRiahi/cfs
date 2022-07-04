package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;

public class FinancialPeriodTypeAssignRequest {
    private Long id;
    private Long organizationId;
    private Long financialPeriodId;
    private LocalDateTime startDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodTypeId) {
        this.financialPeriodId = financialPeriodId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}

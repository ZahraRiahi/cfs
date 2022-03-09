package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;

public class FinancialPeriodRequest {
    private LocalDateTime date;
    private Long financialPeriodTypeId;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getFinancialPeriodTypeId() {
        return financialPeriodTypeId;
    }

    public void setFinancialPeriodTypeId(Long financialPeriodTypeId) {
        this.financialPeriodTypeId = financialPeriodTypeId;
    }
}

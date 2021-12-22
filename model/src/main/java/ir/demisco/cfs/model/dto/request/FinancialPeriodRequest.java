package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;

public class FinancialPeriodRequest {
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

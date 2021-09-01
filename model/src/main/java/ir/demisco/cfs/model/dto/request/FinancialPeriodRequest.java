package ir.demisco.cfs.model.dto.request;

import java.time.LocalDate;

public class FinancialPeriodRequest {
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

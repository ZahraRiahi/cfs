package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialPeriodNewResponse {
    private LocalDateTime startDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public static FinancialPeriodNewResponse.Builder builder() {
        return new FinancialPeriodNewResponse.Builder();
    }

    public static final class Builder {
        private FinancialPeriodNewResponse financialPeriodNewResponse;

        private Builder() {
            financialPeriodNewResponse = new FinancialPeriodNewResponse();
        }

        public static Builder aFinancialPeriodNewResponse() {
            return new Builder();
        }

        public Builder startDate(LocalDateTime startDate) {
            financialPeriodNewResponse.setStartDate(startDate);
            return this;
        }

        public FinancialPeriodNewResponse build() {
            return financialPeriodNewResponse;
        }
    }
}

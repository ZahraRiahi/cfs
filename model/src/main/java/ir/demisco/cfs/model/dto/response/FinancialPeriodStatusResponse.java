package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodStatusResponse {
    private Long periodStatus;
    private Long monthStatus;

    public Long getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(Long periodStatus) {
        this.periodStatus = periodStatus;
    }

    public Long getMonthStatus() {
        return monthStatus;
    }

    public void setMonthStatus(Long monthStatus) {
        this.monthStatus = monthStatus;
    }

    public static FinancialPeriodStatusResponse.Builder builder() {
        return new FinancialPeriodStatusResponse.Builder();
    }

    public static final class Builder {
        private FinancialPeriodStatusResponse financialPeriodStatusResponse;

        private Builder() {
            financialPeriodStatusResponse = new FinancialPeriodStatusResponse();
        }

        public static Builder financialPeriodStatusResponse() {
            return new Builder();
        }

        public Builder periodStatus(Long periodStatus) {
            financialPeriodStatusResponse.setPeriodStatus(periodStatus);
            return this;
        }

        public Builder monthStatus(Long monthStatus) {
            financialPeriodStatusResponse.setMonthStatus(monthStatus);
            return this;
        }

        public FinancialPeriodStatusResponse build() {
            return financialPeriodStatusResponse;
        }
    }
}

package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodTypeResponse {
    private Long id;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static FinancialPeriodTypeResponse.Builder builder() {
        return new FinancialPeriodTypeResponse.Builder();
    }

    public static final class Builder {
        private FinancialPeriodTypeResponse financialPeriodTypeResponse;

        private Builder() {
            financialPeriodTypeResponse = new FinancialPeriodTypeResponse();
        }

        public static Builder aFinancialPeriodTypeResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodTypeResponse.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialPeriodTypeResponse.setDescription(description);
            return this;
        }

        public FinancialPeriodTypeResponse build() {
            return financialPeriodTypeResponse;
        }
    }
}

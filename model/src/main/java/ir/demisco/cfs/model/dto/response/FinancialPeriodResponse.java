package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodResponse {
    private Long id;
    private String description;
    private String code;
    private String fullDescription;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public static FinancialPeriodResponse.Builder builder() {
        return new FinancialPeriodResponse.Builder();
    }

    public static final class Builder {
        private FinancialPeriodResponse financialPeriodResponse;

        private Builder() {
            financialPeriodResponse = new FinancialPeriodResponse();
        }

        public static Builder financialPeriodResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodResponse.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialPeriodResponse.setDescription(description);
            return this;
        }

        public Builder code(String code) {
            financialPeriodResponse.setCode(code);
            return this;
        }

        public Builder fullDescription(String fullDescription) {
            financialPeriodResponse.setFullDescription(fullDescription);
            return this;
        }

        public FinancialPeriodResponse build() {
            return financialPeriodResponse;
        }
    }
}

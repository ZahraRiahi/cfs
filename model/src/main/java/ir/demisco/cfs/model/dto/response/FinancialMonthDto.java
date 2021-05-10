package ir.demisco.cfs.model.dto.response;

public class FinancialMonthDto {
    private Long id;
    private Long financialPeriodId;
    private Long financialMonthStatusId;
    private String financialMonthStatusName;
    private Long financialMonthTypeId;
    private String financialMonthTypeDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
    }

    public Long getFinancialMonthStatusId() {
        return financialMonthStatusId;
    }

    public void setFinancialMonthStatusId(Long financialMonthStatusId) {
        this.financialMonthStatusId = financialMonthStatusId;
    }

    public String getFinancialMonthStatusName() {
        return financialMonthStatusName;
    }

    public void setFinancialMonthStatusName(String financialMonthStatusName) {
        this.financialMonthStatusName = financialMonthStatusName;
    }

    public Long getFinancialMonthTypeId() {
        return financialMonthTypeId;
    }

    public void setFinancialMonthTypeId(Long financialMonthTypeId) {
        this.financialMonthTypeId = financialMonthTypeId;
    }

    public String getFinancialMonthTypeDescription() {
        return financialMonthTypeDescription;
    }

    public void setFinancialMonthTypeDescription(String financialMonthTypeDescription) {
        this.financialMonthTypeDescription = financialMonthTypeDescription;
    }

    public static FinancialMonthDto.Builder builder() {
        return new FinancialMonthDto.Builder();
    }

    public static final class Builder {
        private FinancialMonthDto financialMonthDto;

        private Builder() {
            financialMonthDto = new FinancialMonthDto();
        }

        public static Builder financialMonthDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialMonthDto.setId(id);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialMonthDto.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder financialMonthStatusId(Long financialMonthStatusId) {
            financialMonthDto.setFinancialMonthStatusId(financialMonthStatusId);
            return this;
        }

        public Builder financialMonthStatusName(String financialMonthStatusName) {
            financialMonthDto.setFinancialMonthStatusName(financialMonthStatusName);
            return this;
        }

        public Builder financialMonthTypeId(Long financialMonthTypeId) {
            financialMonthDto.setFinancialMonthTypeId(financialMonthTypeId);
            return this;
        }

        public Builder financialMonthTypeDescription(String financialMonthTypeDescription) {
            financialMonthDto.setFinancialMonthTypeDescription(financialMonthTypeDescription);
            return this;
        }

        public FinancialMonthDto build() {
            return financialMonthDto;
        }
    }
}

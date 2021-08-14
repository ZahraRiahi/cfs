package ir.demisco.cfs.model.dto.response;



import java.util.Date;

public class FinancialPeriodParameterDto {
    private Long id;
    private Long financialPeriodId;
    private Date startDate;
    private Long taxDeductionRate;
    private Long vatTaxRate;
    private Long vatTollRate;
    private Long insuranceDeductionRate;
    private Long maxFewerAmount;
    private Boolean vatFillFlag;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getTaxDeductionRate() {
        return taxDeductionRate;
    }

    public void setTaxDeductionRate(Long taxDeductionRate) {
        this.taxDeductionRate = taxDeductionRate;
    }

    public Long getVatTaxRate() {
        return vatTaxRate;
    }

    public void setVatTaxRate(Long vatTaxRate) {
        this.vatTaxRate = vatTaxRate;
    }

    public Long getVatTollRate() {
        return vatTollRate;
    }

    public void setVatTollRate(Long vatTollRate) {
        this.vatTollRate = vatTollRate;
    }

    public Long getInsuranceDeductionRate() {
        return insuranceDeductionRate;
    }

    public void setInsuranceDeductionRate(Long insuranceDeductionRate) {
        this.insuranceDeductionRate = insuranceDeductionRate;
    }

    public Long getMaxFewerAmount() {
        return maxFewerAmount;
    }

    public void setMaxFewerAmount(Long maxFewerAmount) {
        this.maxFewerAmount = maxFewerAmount;
    }

    public Boolean getVatFillFlag() {
        return vatFillFlag;
    }

    public void setVatFillFlag(Boolean vatFillFlag) {
        this.vatFillFlag = vatFillFlag;
    }

    public static FinancialPeriodParameterDto.Builder builder() {
        return new FinancialPeriodParameterDto.Builder();
    }


    public static final class Builder {
        private FinancialPeriodParameterDto financialPeriodParameterDto;

        private Builder() {
            financialPeriodParameterDto = new FinancialPeriodParameterDto();
        }

        public static Builder financialPeriodParameterDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodParameterDto.setId(id);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialPeriodParameterDto.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder startDate(Date startDate) {
            financialPeriodParameterDto.setStartDate(startDate);
            return this;
        }

        public Builder taxDeductionRate(Long taxDeductionRate) {
            financialPeriodParameterDto.setTaxDeductionRate(taxDeductionRate);
            return this;
        }

        public Builder vatTaxRate(Long vatTaxRate) {
            financialPeriodParameterDto.setVatTaxRate(vatTaxRate);
            return this;
        }

        public Builder vatTollRate(Long vatTollRate) {
            financialPeriodParameterDto.setVatTollRate(vatTollRate);
            return this;
        }

        public Builder insuranceDeductionRate(Long insuranceDeductionRate) {
            financialPeriodParameterDto.setInsuranceDeductionRate(insuranceDeductionRate);
            return this;
        }

        public Builder maxFewerAmount(Long maxFewerAmount) {
            financialPeriodParameterDto.setMaxFewerAmount(maxFewerAmount);
            return this;
        }

        public Builder vatFillFlag(Boolean vatFillFlag) {
            financialPeriodParameterDto.setVatFillFlag(vatFillFlag);
            return this;
        }

        public FinancialPeriodParameterDto build() {
            return financialPeriodParameterDto;
        }

    }
}

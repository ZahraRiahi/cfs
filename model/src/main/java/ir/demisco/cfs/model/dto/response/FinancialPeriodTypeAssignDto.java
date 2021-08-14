package ir.demisco.cfs.model.dto.response;


import java.time.LocalDateTime;


public class FinancialPeriodTypeAssignDto {
    private Long id;
    private Long organizationId;
    private Long financialPeriodTypeId;
    private String financialPeriodTypeDescription;
    private Long activeFlag;
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

    public Long getFinancialPeriodTypeId() {
        return financialPeriodTypeId;
    }

    public void setFinancialPeriodTypeId(Long financialPeriodTypeId) {
        this.financialPeriodTypeId = financialPeriodTypeId;
    }

    public String getFinancialPeriodTypeDescription() {
        return financialPeriodTypeDescription;
    }

    public void setFinancialPeriodTypeDescription(String financialPeriodTypeDescription) {
        this.financialPeriodTypeDescription = financialPeriodTypeDescription;
    }

    public Long getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Long activeFlag) {
        this.activeFlag = activeFlag;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private FinancialPeriodTypeAssignDto financialPeriodTypeAssignDto;

        private Builder() {
            financialPeriodTypeAssignDto = new FinancialPeriodTypeAssignDto();
        }

        public static Builder financialPeriodTypeAssignDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodTypeAssignDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialPeriodTypeAssignDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialPeriodTypeId(Long financialPeriodTypeId) {
            financialPeriodTypeAssignDto.setFinancialPeriodTypeId(financialPeriodTypeId);
            return this;
        }

        public Builder financialPeriodTypeDescription(String financialPeriodTypeDescription) {
            financialPeriodTypeAssignDto.setFinancialPeriodTypeDescription(financialPeriodTypeDescription);
            return this;
        }

        public Builder activeFlag(Long activeFlag) {
            financialPeriodTypeAssignDto.setActiveFlag(activeFlag);
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            financialPeriodTypeAssignDto.setStartDate(startDate);
            return this;
        }

        public FinancialPeriodTypeAssignDto build() {
            return financialPeriodTypeAssignDto;
        }
    }
}

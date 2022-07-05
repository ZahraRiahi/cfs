package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialPeriodTypeAssignSaveDto {

    private Long id;
    private Long organizationId;
    private Long financialPeriodId;
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

    public Long getFinancialPeriodId() {
        return financialPeriodId;
    }

    public void setFinancialPeriodId(Long financialPeriodId) {
        this.financialPeriodId = financialPeriodId;
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
        private FinancialPeriodTypeAssignSaveDto financialPeriodTypeAssignSaveDto;

        private Builder() {
            financialPeriodTypeAssignSaveDto = new FinancialPeriodTypeAssignSaveDto();
        }

        public static Builder financialPeriodTypeAssignSaveDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodTypeAssignSaveDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialPeriodTypeAssignSaveDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialPeriodId(Long financialPeriodId) {
            financialPeriodTypeAssignSaveDto.setFinancialPeriodId(financialPeriodId);
            return this;
        }

        public Builder activeFlag(Long activeFlag) {
            financialPeriodTypeAssignSaveDto.setActiveFlag(activeFlag);
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            financialPeriodTypeAssignSaveDto.setStartDate(startDate);
            return this;
        }

        public FinancialPeriodTypeAssignSaveDto build() {
            return financialPeriodTypeAssignSaveDto;
        }
    }
}

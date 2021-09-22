package ir.demisco.cfs.model.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FinancialPeriodDto {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long openMonthCount;
    private Long financialPeriodTypeAssignId;
    private Long statusId;
    private String statusCode;
    private String statusName;
    private String description;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getOpenMonthCount() {
        return openMonthCount;
    }

    public void setOpenMonthCount(Long openMonthCount) {
        this.openMonthCount = openMonthCount;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public static Builder builder() {
        return new Builder();
    }

    public Long getFinancialPeriodTypeAssignId() {
        return financialPeriodTypeAssignId;
    }

    public void setFinancialPeriodTypeAssignId(Long financialPeriodTypeAssignId) {
        this.financialPeriodTypeAssignId = financialPeriodTypeAssignId;
    }

    public static final class Builder {
        private FinancialPeriodDto financialPeriodDto;

        private Builder() {
            financialPeriodDto = new FinancialPeriodDto();
        }

        public static Builder financialPeriodDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodDto.setId(id);
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            financialPeriodDto.setStartDate(startDate);
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            financialPeriodDto.setEndDate(endDate);
            return this;
        }

        public Builder openMonthCount(Long openMonthCount) {
            financialPeriodDto.setOpenMonthCount(openMonthCount);
            return this;
        }

        public Builder financialPeriodTypeAssignId(Long financialPeriodTypeAssignId) {
            financialPeriodDto.setFinancialPeriodTypeAssignId(financialPeriodTypeAssignId);
            return this;
        }


        public Builder statusId(Long statusId) {
            financialPeriodDto.setStatusId(statusId);
            return this;
        }

        public Builder statusCode(String statusCode) {
            financialPeriodDto.setStatusCode(statusCode);
            return this;
        }

        public Builder statusName(String statusName) {
            financialPeriodDto.setStatusName(statusName);
            return this;
        }
        public Builder description(String description) {
            financialPeriodDto.setDescription(description);
            return this;
        }
        public Builder code(String code) {
            financialPeriodDto.setCode(code);
            return this;
        }

        public FinancialPeriodDto build() {
            return financialPeriodDto;
        }
    }

}

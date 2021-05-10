package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialPeriodDto {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Long openMonthCount;
    private Long financialPeriodTypeAssignId;
    private Long statusId;
    private String statusCode;
    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

        public Builder startDate(Date startDate) {
            financialPeriodDto.setStartDate(startDate);
            return this;
        }

        public Builder endDate(Date endDate) {
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

        public FinancialPeriodDto build() {
            return financialPeriodDto;
        }
    }

}

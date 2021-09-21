package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialPeriodDateDto {

    private Date startDate;
    private Date endDate;

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

    public static Builder builder(){
        return new Builder();
     }


    public static final class Builder {
        private FinancialPeriodDateDto financialPeriodDateDto;

        private Builder() {
            financialPeriodDateDto = new FinancialPeriodDateDto();
        }

        public static Builder aFinancialPeriodDateDto() {
            return new Builder();
        }

        public Builder startDate(Date startDate) {
            financialPeriodDateDto.setStartDate(startDate);
            return this;
        }

        public Builder endDate(Date endDate) {
            financialPeriodDateDto.setEndDate(endDate);
            return this;
        }

        public FinancialPeriodDateDto build() {
            return financialPeriodDateDto;
        }
    }
}

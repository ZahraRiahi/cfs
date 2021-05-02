package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodStatusDto {

    private Long id;
    private String name;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Builder  builder(){
        return new Builder();
    }


    public static final class Builder {
        private FinancialPeriodStatusDto financialPeriodStatusDto;

        private Builder() {
            financialPeriodStatusDto = new FinancialPeriodStatusDto();
        }

        public static Builder financialPeriodStatusDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodStatusDto.setId(id);
            return this;
        }

        public Builder name(String name) {
            financialPeriodStatusDto.setName(name);
            return this;
        }

        public Builder code(String code) {
            financialPeriodStatusDto.setCode(code);
            return this;
        }

        public FinancialPeriodStatusDto build() {
            return financialPeriodStatusDto;
        }
    }
}

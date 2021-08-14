package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "financial_period_parameter", schema = "fnpr")
public class FinancialPeriodParameter extends AuditModel<Long> {
    private Long id;
    private FinancialPeriod financialPeriod;
    private Date startDate;
    private Long taxDeductionRate;
    private Long vatTaxRate;
    private Long vatTollRate;
    private Long insuranceDeductionRate;
    private Long maxFewerAmount;
    private Boolean vatFillFlag;

    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_period_parameter_generator", sequenceName = "sq_financial_period_parameter", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_period_parameter_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_PERIOD_ID")
    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    @Column(name = "TAX_DEDUCTION_RATE")
    public Long getTaxDeductionRate() {
        return taxDeductionRate;
    }

    public void setTaxDeductionRate(Long taxDeductionRate) {
        this.taxDeductionRate = taxDeductionRate;
    }

    @Column(name = "VAT_TAX_RATE")
    public Long getVatTaxRate() {
        return vatTaxRate;
    }

    public void setVatTaxRate(Long vatTaxRate) {
        this.vatTaxRate = vatTaxRate;
    }

    @Column(name = "VAT_TOLL_RATE")
    public Long getVatTollRate() {
        return vatTollRate;
    }

    public void setVatTollRate(Long vatTollRate) {
        this.vatTollRate = vatTollRate;
    }

    @Column(name = "INSURANCE_DEDUCTION_RATE")
    public Long getInsuranceDeductionRate() {
        return insuranceDeductionRate;
    }

    public void setInsuranceDeductionRate(Long insuranceDeductionRate) {
        this.insuranceDeductionRate = insuranceDeductionRate;
    }

    @Column(name = "MAX_FEWER_AMOUNT")
    public Long getMaxFewerAmount() {
        return maxFewerAmount;
    }

    public void setMaxFewerAmount(Long maxFewerAmount) {
        this.maxFewerAmount = maxFewerAmount;
    }

    @Column(name = "VAT_FILL_FLAG")
    public Boolean getVatFillFlag() {
        return vatFillFlag;
    }

    public void setVatFillFlag(Boolean vatFillFlag) {
        this.vatFillFlag = vatFillFlag;
    }
}

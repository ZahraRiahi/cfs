package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "financial_month_type", schema = "fnpr")
public class FinancialMonthType extends AuditModel<Long> {
    private Long id;
    private FinancialPeriod financialPeriod;
    private String description;
    private Long monthNumber;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_PERIOD_TYPE_ID")
    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "MONTH_NUMBER")
    public Long getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Long monthNumber) {
        this.monthNumber = monthNumber;
    }
}

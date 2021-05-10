package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;


@Entity
@Table(name = "financial_month", schema = "fnpr")
public class FinancialMonth extends AuditModel<Long> {
    private Long id;
    private FinancialPeriod financialPeriod;
    private FinancialMonthStatus financialMonthStatus;
    private FinancialMonthType financialMonthType;

    @Id
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_MONTH_STATUS_ID")
    public FinancialMonthStatus getFinancialMonthStatus() {
        return financialMonthStatus;
    }

    public void setFinancialMonthStatus(FinancialMonthStatus financialMonthStatus) {
        this.financialMonthStatus = financialMonthStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_MONTH_TYPE_ID")
    public FinancialMonthType getFinancialMonthType() {
        return financialMonthType;
    }

    public void setFinancialMonthType(FinancialMonthType financialMonthType) {
        this.financialMonthType = financialMonthType;
    }
}

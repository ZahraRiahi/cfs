package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_LEDGER_MONTH", schema = "fndc")
public class FinancialLedgerMonth extends AuditModel<Long> {
    private Long id;
    private FinancialLedgerMonthStatus financialLedgerMonthStatus;
    private FinancialLedgerType financialLedgerType;
    private FinancialMonth financialMonth;
    private FinancialLedgerPeriod financialLedgerPeriod;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_ledger_month_generator", sequenceName = "Sq_financial_ledger_month")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_ledger_month_generator")
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIN_LEDGER_MONTH_STAT_ID")
    public FinancialLedgerMonthStatus getFinancialLedgerMonthStatus() {
        return financialLedgerMonthStatus;
    }

    public void setFinancialLedgerMonthStatus(FinancialLedgerMonthStatus financialLedgerMonthStatus) {
        this.financialLedgerMonthStatus = financialLedgerMonthStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_LEDGER_TYPE_ID")
    public FinancialLedgerType getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(FinancialLedgerType financialLedgerType) {
        this.financialLedgerType = financialLedgerType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_MONTH_ID")
    public FinancialMonth getFinancialMonth() {
        return financialMonth;
    }

    public void setFinancialMonth(FinancialMonth financialMonth) {
        this.financialMonth = financialMonth;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_LEDGER_PERIOD_ID")
    public FinancialLedgerPeriod getFinancialLedgerPeriod() {
        return financialLedgerPeriod;
    }

    public void setFinancialLedgerPeriod(FinancialLedgerPeriod financialLedgerPeriod) {
        this.financialLedgerPeriod = financialLedgerPeriod;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

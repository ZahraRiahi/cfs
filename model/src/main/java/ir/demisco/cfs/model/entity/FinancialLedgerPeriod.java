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
@Table(name = "FINANCIAL_LEDGER_PERIOD", schema = "fndc")
public class FinancialLedgerPeriod extends AuditModel<Long> {
    private Long id;
    private FinancialLedgerPeriodStatus financialLedgerPeriodStatus;
    private FinancialPeriod financialPeriod;
    private FinancialLedgerType financialLedgerType;
    private FinancialDocument financialDocumentOpening;
    private FinancialDocument financialDocumentPermanent;
    private FinancialDocument financialDocumentTemprory;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_ledger_period_generator", sequenceName = "sq_financial_ledger_period")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_ledger_period_generator")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIN_LEDGER_PERIOD_STAT_ID")
    public FinancialLedgerPeriodStatus getFinancialLedgerPeriodStatus() {
        return financialLedgerPeriodStatus;
    }

    public void setFinancialLedgerPeriodStatus(FinancialLedgerPeriodStatus financialLedgerPeriodStatus) {
        this.financialLedgerPeriodStatus = financialLedgerPeriodStatus;
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
    @JoinColumn(name = "FINANCIAL_LEDGER_TYPE_ID")
    public FinancialLedgerType getFinancialLedgerType() {
        return financialLedgerType;
    }

    public void setFinancialLedgerType(FinancialLedgerType financialLedgerType) {
        this.financialLedgerType = financialLedgerType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_OPENING_ID")
    public FinancialDocument getFinancialDocumentOpening() {
        return financialDocumentOpening;
    }

    public void setFinancialDocumentOpening(FinancialDocument financialDocumentOpening) {
        this.financialDocumentOpening = financialDocumentOpening;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_PERMANENT_ID")
    public FinancialDocument getFinancialDocumentPermanent() {
        return financialDocumentPermanent;
    }

    public void setFinancialDocumentPermanent(FinancialDocument financialDocumentPermanent) {
        this.financialDocumentPermanent = financialDocumentPermanent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_TEMPRORY_ID")
    public FinancialDocument getFinancialDocumentTemprory() {
        return financialDocumentTemprory;
    }

    public void setFinancialDocumentTemprory(FinancialDocument financialDocumentTemprory) {
        this.financialDocumentTemprory = financialDocumentTemprory;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

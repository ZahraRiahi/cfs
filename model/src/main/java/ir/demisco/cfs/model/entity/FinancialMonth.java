package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "financial_month", schema = "fnpr")
public class FinancialMonth extends AuditModel<Long> {
    private Long id;
    private FinancialPeriod financialPeriod;
    private FinancialMonthStatus financialMonthStatus;
    private FinancialMonthType financialMonthType;
    private Date startDate;
    private Date endDate;
    private String    description;
    private LocalDateTime DeletedDate;

    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_month_generator", sequenceName = "sq_financial_month")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_month_generator")
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

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return DeletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        DeletedDate = deletedDate;
    }
}

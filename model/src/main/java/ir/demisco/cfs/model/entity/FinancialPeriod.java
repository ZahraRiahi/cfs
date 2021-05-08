package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "financial_period", schema = "fnpr")
public class FinancialPeriod extends AuditModel<Long> {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Long openMonthCount;
    private FinancialPeriodStatus financialPeriodStatus;
    private FinancialPeriodTypeAssign financialPeriodTypeAssign;

    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_period_generator", sequenceName = "sq_financial_period")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_period_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_PERIOD_STATUS_ID")
    public FinancialPeriodStatus getFinancialPeriodStatus() {
        return financialPeriodStatus;
    }

    public void setFinancialPeriodStatus(FinancialPeriodStatus financialPeriodStatus) {
        this.financialPeriodStatus = financialPeriodStatus;
    }


    @Column(name = "OPEN_MONTH_COUNT")
    public Long getOpenMonthCount() {
        return openMonthCount;
    }

    public void setOpenMonthCount(Long openMonthCount) {
        this.openMonthCount = openMonthCount;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINAN_PERIOD_TYPE_ASSIGN_ID")
    public FinancialPeriodTypeAssign getFinancialPeriodTypeAssign() {
        return financialPeriodTypeAssign;
    }

    public void setFinancialPeriodTypeAssign(FinancialPeriodTypeAssign financialPeriodTypeAssign) {
        this.financialPeriodTypeAssign = financialPeriodTypeAssign;
    }
}

package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

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
@Table(name = "financial_period_type_assign", schema = "fnpr")
public class FinancialPeriodTypeAssign extends AuditModel<Long> {

    private Organization organization;
    private FinancialPeriod financialPeriod;
    private Long activeFlag;
    private LocalDateTime startDate;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_period_type_assign_generator", sequenceName = "sq_financial_period_type_assign", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_period_type_assign_generator")
    public Long getId() {
        return super.getId();
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
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "ACTIVE_FLAG")
    public Long getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Long activeFlag) {
        this.activeFlag = activeFlag;
    }


    @Column(name = "START_DATE")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }


    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

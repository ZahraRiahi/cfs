package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;

@Entity
@Table(name = "financial_period_type_assign", schema = "fnpr")
public class FinancialPeriodTypeAssign extends AuditModel<Long> {

    private Long id;
    private Organization organization;
    private FinancialPeriodType financialPeriodType;
    private Long activeFlag;

    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_period_type_assign_generator", sequenceName = "sq_financial_period_type_assign", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_period_type_assign_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_PERIOD_TYPE_ID")
    public FinancialPeriodType getFinancialPeriodType() {
        return financialPeriodType;
    }

    public void setFinancialPeriodType(FinancialPeriodType financialPeriodType) {
        this.financialPeriodType = financialPeriodType;
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
}

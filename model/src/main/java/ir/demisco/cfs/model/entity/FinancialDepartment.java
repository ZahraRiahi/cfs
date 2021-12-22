package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DEPARTMENT" , schema = "fndc")
public class FinancialDepartment extends AuditModel<Long> {

    private String code;
    private String name;
    private LocalDateTime deletedDate;
    private Organization organization;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_department_generator", sequenceName = "Sq_Financial_Department")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_department_generator")
    public Long getId() {
        return super.getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}

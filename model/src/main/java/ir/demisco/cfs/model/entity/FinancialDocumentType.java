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
@Table(name = "FINANCIAL_DOCUMENT_TYPE", schema = "fndc")
public class FinancialDocumentType extends AuditModel<Long> {
    private Long id;
    private Organization organization;
    private String description;
    private Boolean activeFlag;
    private Boolean automaticFlag;
    private FinancialSystem financialSystem;
    private LocalDateTime deletedDate;
    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_type_generator", sequenceName = "sq_financial_document_type")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_type_generator")
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Boolean getAutomaticFlag() {
        return automaticFlag;
    }

    public void setAutomaticFlag(Boolean automaticFlag) {
        this.automaticFlag = automaticFlag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_SYSTEM_ID")
    public FinancialSystem getFinancialSystem() {
        return financialSystem;
    }

    public void setFinancialSystem(FinancialSystem financialSystem) {
        this.financialSystem = financialSystem;
    }

    @Column(name = "DELETED_DATE")

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

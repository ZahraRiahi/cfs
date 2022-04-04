package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_STATUS" , schema = "fndc")
public class FinancialDocumentStatus extends AuditModel<Long> {

    private String code;
    private String name;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_Status_generator", sequenceName = "sq_Financial_Document_Status")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_Status_generator")
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

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

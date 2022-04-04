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
@Table(name="FINANCIAL_SYSTEM" , schema = "fndc")
public class FinancialSystem extends AuditModel<Long> {

    private String   description;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_system_generator", sequenceName = "sq_financial_system")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_system_generator")
    public Long getId() {
        return super.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "DELETED_DATE")

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

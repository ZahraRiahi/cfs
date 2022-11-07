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
@Table(name = "FINANCIAL_LEDGER_MONTH_STATUS", schema = "fndc")
public class FinancialLedgerMonthStatus extends AuditModel<Long> {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_ledger_month_status_generator", sequenceName = "Sq_financial_ledger_month_status")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_ledger_month_status_generator")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}

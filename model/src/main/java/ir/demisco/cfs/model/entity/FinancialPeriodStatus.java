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
@Table(name = "FINANCIAL_PERIOD_STATUS", schema = "fnpr")
public class FinancialPeriodStatus extends AuditModel<Long> {
    private Long id;
    private String code;
    private String name;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fnpr", name = "FINANCIAL_PERIOD_STATUS_generator", sequenceName = "sq_FINANCIAL_PERIOD_STATUS", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FINANCIAL_PERIOD_STATUS_generator")
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

    @Column(name = "NAME")
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

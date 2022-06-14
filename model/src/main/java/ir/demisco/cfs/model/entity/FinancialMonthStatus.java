package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "financial_month_status", schema = "fnpr")
public class FinancialMonthStatus extends AuditModel<Long> {
    private Long id;
    private String code;
    private String name;
    @Override
    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_month_status_generator", sequenceName = "sq_financial_month_status")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_month_status_generator")
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


}

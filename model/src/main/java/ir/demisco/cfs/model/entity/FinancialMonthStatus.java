package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "financial_month_status", schema = "fnpr")
public class FinancialMonthStatus extends AuditModel<Long> {
    private Long id;
    private String code;
    private String name;

    @Id
    public Long getId() {
        return id;
    }

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

    public enum Code {
        OPEN("OPEN"), CLOSE("CLOSE");

        Code(String code) {
            this.code = code;
        }

        String code;

        public String getCode() {
            return code;
        }

    }

}

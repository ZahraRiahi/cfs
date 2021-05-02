package ir.demisco.cfs.model.entity;

import javax.persistence.*;


@Entity
@Table(name = "FINANCIAL_PERIOD_STATUS", schema = "fnpr")
public class FinancialPeriodStatus {
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

}

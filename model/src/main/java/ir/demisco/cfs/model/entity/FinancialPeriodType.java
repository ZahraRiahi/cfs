package ir.demisco.cfs.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "financial_period_type", schema = "fnpr")
public class FinancialPeriodType {
    private Long ID;
    private String Description;
    private Long FromMonth;
    private Long ToMonth;
    private Long CalendarTypeId;

    @Id
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Column(name = "FROM_MONTH")
    public Long getFromMonth() {
        return FromMonth;
    }

    public void setFromMonth(Long fromMonth) {
        FromMonth = fromMonth;
    }

    @Column(name = "TO_MONTH")
    public Long getToMonth() {
        return ToMonth;
    }

    public void setToMonth(Long toMonth) {
        ToMonth = toMonth;
    }

    @Column(name = "CALENDAR_TYPE_ID")
    public Long getCalendarTypeId() {
        return CalendarTypeId;
    }

    public void setCalendarTypeId(Long calendarTypeId) {
        CalendarTypeId = calendarTypeId;
    }
}

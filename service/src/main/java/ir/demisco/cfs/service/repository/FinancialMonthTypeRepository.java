package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialMonthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialMonthTypeRepository extends JpaRepository<FinancialMonthType,Long> {

@Query(value = "select month_type_id, " +
        "       (select case calendar_type_id " +
        "                 when 1 then " +
        "                  min(ad.pdat_ggdate_c) " +
        "                 when 2 then " +
        "                  min(ad.pdat_ggdate_c) " +
        "               end " +
        "          from clnd.all_date ad " +
        "         where (calendar_type_id = 1 and " +
        "               ad.pdat_hsdate_yer =substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4) " +
        "               And " +
        "               ad.pdat_hsdate_mon =substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),6,2)) " +
        "            or " +
        "              (calendar_type_id = 2 and " +
        "               ad.pdat_ggdate_yer =extract(year from TO_DATE(start_date_final, 'mm/dd/yyyy')) and " +
        "               ad.pdat_ggdate_mon =extract(month from TO_DATE(start_date_final, 'mm/dd/yyyy')))) start_date, " +
        "       (select case calendar_type_id " +
        "                 when 1 then " +
        "                  max(ad.pdat_ggdate_c) " +
        "                 when 2 then " +
        "                  max(ad.pdat_ggdate_c) " +
        "               end " +
        "          from clnd.all_date ad " +
        "         where (calendar_type_id = 1 and " +
        "               ad.pdat_hsdate_yer =substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4) " +
        "               And " +
        "               ad.pdat_hsdate_mon =substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),6, 2)) " +
        "            or " +
        "              (calendar_type_id = 2 and " +
        "               ad.pdat_ggdate_yer = extract(year from TO_DATE(start_date_final, 'mm/dd/yyyy')) and " +
        "               ad.pdat_ggdate_mon =extract(month from TO_DATE(start_date_final, 'mm/dd/yyyy')))" +
        "        ) end_date, " +
        "       description || ' ' || " +
        "       case calendar_type_id " +
        "         when 1 then " +
        "          substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy'),'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4) " +
        "         when 2 then " +
        "          TO_CHAR(extract(year from TO_DATE(start_date_final, 'mm/dd/yyyy'))) " +
        "       End " +
        "  from (select fm.description, " +
        "               fm.month_number, " +
        "               fm.id month_type_id, " +
        "               fnp.calendar_type_id, " +
        "               TO_CHAR(TO_DATE(TO_CHAR(add_months((start_date + 1),month_number - 1),'mm/dd/yyyy'),'mm/dd/yyyy') - 1,'mm/dd/yyyy') start_date_final, " +
        "               case (select distinct 1 " +
        "                   from fnpr.financial_month fnm " +
        "                  where fnm.financial_period_id = :financialPeriodId " +
        "                    And fnm.deleted_date is null) " +
        "                 when 1 then " +
        "                  (select max(fnm.end_date) + 1 " +
        "                     from fnpr.financial_month fnm " +
        "                    where fnm.financial_period_id = :financialPeriodId " +
        "                      and fnm.deleted_date is null) " +
        "                 else " +
        "                  (select fp.start_date " +
        "                     from fnpr.financial_period fp " +
        "                    where fp.id = :financialPeriodId " +
        "                      and fp.deleted_date is null) " +
        "               end start_date " +
        "          from fnpr.financial_period_type fnp " +
        "         inner join fnpr.financial_month_type fm " +
        "            on fnp.id = fm.financial_period_type_id " +
        "           and fm.deleted_date is null " +
        "         inner join fnpr.financial_period_type_assign ta " +
        "            on ta.financial_period_type_id = fnp.id " +
        "           and ta.deleted_date is null " +
        "           and ta.active_flag = 1 " +
        "           and ta.organization_id = :organizationId " +
        "         where fnp.deleted_date is null)", nativeQuery = true)
List<Object[]> findByParam(Long organizationId,Long financialPeriodId);
}

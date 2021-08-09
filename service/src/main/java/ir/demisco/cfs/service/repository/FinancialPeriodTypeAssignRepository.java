package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FinancialPeriodTypeAssignRepository extends JpaRepository<FinancialPeriodTypeAssign, Long> {
    @Query("select fpta from FinancialPeriodTypeAssign fpta where fpta.organization.id=:organizationId")
    List<FinancialPeriodTypeAssign> findByOrganizationId(Long organizationId);

    @Query("select fpa from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId ")
    Optional<FinancialPeriodTypeAssign> getFinancialPeriodTypeAssignId(Long organizationId);

    @Query(value = " select fnta.start_date," +
            "       (select case fnpt.calendar_type_id " +
            "                 when 1 then " +
            "                  max(ad.pdat_ggdate_c) " +
            "                 when 2 then " +
            "                  max(ad.pdat_ggdate_c) " +
            "               end " +
            "          from clnd.all_date ad " +
            "         where (fnpt.calendar_type_id = 1 and " +
            "               ad.pdat_hsdate_yer = substr(TO_CHAR(fnta.start_date,'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4)) " +
            "            or " +
            "               (fnpt.calendar_type_id = 2 and " +
            "               ad.pdat_ggdate_yer = extract(year from fnta.start_date))" +
            "        ) end_date " +
            "  from fnpr.financial_period_type_assign fnta " +
            "  inner join fnpr.financial_period_type fnpt " +
            "    on fnta.financial_period_type_id = fnpt.id " +
            "    and fnta.deleted_date is null   " +
            "  where organization_id = :organizationId " +
            "   And active_flag = 1 " +
            "   and fnta.deleted_date is null" , nativeQuery = true)
    List<Object[]> getStartDateAndEndDate(Long organizationId);


     @Query(value=" select start_date, " +
             "      (select case calendar_type_id " +
             "                 when 1 then " +
             "                  max(ad.pdat_ggdate_c) " +
             "                 when 2 then " +
             "                  max(ad.pdat_ggdate_c) " +
             "               end " +
             "          from clnd.all_date ad " +
             "         where (calendar_type_id = 1 and " +
             "               ad.pdat_hsdate_yer = substr(TO_CHAR(start_date,'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4)) " +
             "            or  " +
             "               (calendar_type_id = 2 and " +
             "               ad.pdat_ggdate_yer = extract(year from start_date)) " +
             "        ) end_date " +
             "  from (select max(fnpr.end_date) + 1 start_date, calendar_type_id " +
             "          from fnpr.financial_period fnpr " +
             "         inner join fnpr.financial_period_type_assign ta " +
             "            on fnpr.finan_period_type_assign_id = ta.id " +
             "            and ta.deleted_date is null " +
             "           and ta.organization_id = :organizationId " +
             "           And ta.active_flag = 1 " +
             "         inner join fnpr.financial_period_type fnpt " +
             "            on fnpt.id = ta.financial_period_type_id " +
             "            and fnpt.deleted_date is null   " +
             "        where fnpr.deleted_date is null " +
             "    group by calendar_type_id) " , nativeQuery = true)
    List<Object[]> getStartDateAndEndDateActive(Long organizationId);
}

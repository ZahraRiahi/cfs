package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FinancialPeriodTypeAssignRepository extends JpaRepository<FinancialPeriodTypeAssign, Long> {
    @Query("select fpta from FinancialPeriodTypeAssign fpta where fpta.organization.id=:organizationId and fpta.deletedDate is null")
    List<FinancialPeriodTypeAssign> findByOrganizationId(Long organizationId);

    @Query("select fpa from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId ")
    Optional<FinancialPeriodTypeAssign> getFinancialPeriodTypeAssignId(Long organizationId);

    @Query("select 1 from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId and  fpa.financialPeriodType.id=:financialPeriodTypeId ")
    Long getFinancialPeriodTypeAssignAndOrganAndPeriodTypeAndStartDate(Long organizationId, Long financialPeriodTypeId);
    @Query(value = " select " +
            "    TO_CHAR(start_date ,'yyyy/mm/dd') start_date, " +
            "    (select max(ad.pdat_ggdate_c) " +
            "    from clnd.all_date1 ad " +
            "   where (calendar_type_id = 1 and " +
            "         ad.pdat_hsdate_yer = substr(TO_CHAR(add_months((start_date), 11) ,'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4) and " +
            "         ad.pdat_hsdate_mon = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd','NLS_CALENDAR=persian'),6,2)) " +
            "      or " +
            "         (calendar_type_id = 2 and " +
            "         ad.pdat_ggdate_yer = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd'),0,4) " +
            "         and " +
            "         ad.pdat_ggdate_mon = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd'),6,2))) end_date " +
            "  from (select case " +
            "                 when max(fnpr.end_date) is null then " +
            "                 ta.start_date " +
            "                 else " +
            "                  max(fnpr.end_date) + 1  " +
            "               end start_date, " +
            "               calendar_type_id " +
            "          from fnpr.financial_period_type_assign ta " +
            "          left outer join fnpr.financial_period fnpr " +
            "            on fnpr.deleted_date is null " +
            "           and fnpr.finan_period_type_assign_id = ta.id " +
            "         inner join fnpr.financial_period_type fnpt " +
            "            on fnpt.id = ta.financial_period_type_id " +
            "           and fnpt.deleted_date is null " +
            "         where ta.deleted_date is null " +
            "           and ta.organization_id = :organizationId " +
            "           And ta.active_flag = 1 " +
            "         group by calendar_type_id, ta.start_date)" , nativeQuery = true)
    List<Object[]> getStartDateAndEndDate(Long organizationId);

    @Query("select fpa from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId and fpa.deletedDate is null ")
    Optional<FinancialPeriodTypeAssign> getFinancialPeriodTypeAssignIdAndOrgan(Long organizationId);
}

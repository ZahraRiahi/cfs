package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FinancialPeriodTypeAssignRepository extends JpaRepository<FinancialPeriodTypeAssign, Long> {
    @Query(value = "  select * " +
            "  from fnpr.financial_period_type_assign fpta" +
            " inner join fnpr.financial_period fnpr" +
            "    on fpta.financial_period_id = fnpr.id" +
            " inner join fnpr.financial_period_type fnpt" +
            "    on fnpr.financial_period_type_id = fnpt.id" +
            "   and fnpt.deleted_date is null" +
            " where fpta.organization_id = :organizationId" +
            "   and fpta.active_flag = 1" +
            "   and fpta.deleted_date is null  ", nativeQuery = true)
    List<FinancialPeriodTypeAssign> findByOrganizationId(Long organizationId);


    @Query("select fpa from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId ")
    Optional<FinancialPeriodTypeAssign> getFinancialPeriodTypeAssignId(Long organizationId);

    @Query("select 1 from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId and  fpa.financialPeriod.id=:financialPeriodId and fpa.startDate=:startDate")
    Long getFinancialPeriodTypeAssignAndOrganAndPeriodTypeAndStartDate(Long organizationId, Long financialPeriodId,LocalDateTime startDate);

    @Query(value = " select " +
            "    TO_CHAR(start_date ,'yyyy/mm/dd') start_date, " +
            "    (select max(ad.pdat_ggdate_c) " +
            "    from clnd.all_date ad " +
            "   where (calendar_type_id = 1 and " +
            "         ad.pdat_hsdate_yer = substr(TO_CHAR(add_months((start_date), 11) ,'yyyy/mm/dd','NLS_CALENDAR=persian'),0,4) and " +
            "         ad.pdat_hsdate_mon = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd','NLS_CALENDAR=persian'),6,2)) " +
            "      or " +
            "         (calendar_type_id = 2 and " +
            "         ad.pdat_ggdate_yer = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd'),0,4) " +
            "         and " +
            "         ad.pdat_ggdate_mon = substr(TO_CHAR(add_months((start_date), 11),'yyyy/mm/dd'),6,2))) end_date " +
            "  from (select max( case" +
            "                 when fnpr.end_date is null then" +
            "                  ta.start_date" +
            "                 else" +
            "                  fnpr.end_date + 1" +
            "               end) start_date, calendar_type_id" +
            "           from fnpr.financial_period fnpr" +
            "          inner join fnpr.financial_period_type_assign ta" +
            "             on ta.financial_period_id = fnpr.id" +
            "          inner join fnpr.financial_period_type fnpt" +
            "             on fnpt.id = fnpr.financial_period_type_id " +
            "         where ta.organization_id = :organizationId " +
            "           And ta.active_flag = 1 " +
            " and  ( :financialPeriodType is null or fnpt.id = :financialPeriodTypeId)" +
            "         group by calendar_type_id)", nativeQuery = true)
    List<Object[]> getStartDateAndEndDate(Long organizationId, Object financialPeriodType, Long financialPeriodTypeId);

    @Query("select fpa from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId and fpa.deletedDate is null ")
    Optional<FinancialPeriodTypeAssign> getFinancialPeriodTypeAssignIdAndOrgan(Long organizationId);

    @Query(value = "select fpa from  FinancialPeriodTypeAssign fpa  join  fpa.financialPeriod fp join  fp.financialPeriodStatus fps left outer join fp.financialPeriodType fpt " +
            " where fpa.organization.id=:organizationId and fps.code =:statusCode and fpa.activeFlag=1 and fpt.id=:financialPeriodTypeId and fp.deletedDate is null order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodTypeAssignOrganizationId(Long organizationId, String statusCode, Long financialPeriodTypeId);


    @Query(value = "select fpa from  FinancialPeriodTypeAssign fpa  join  fpa.financialPeriod fp join  fp.financialPeriodStatus fps left outer join fp.financialPeriodType fpt" +
            " where fpa.organization.id=:organizationId and  fps.code =:statusCode and fpa.activeFlag=1 and fpt.id=:financialPeriodTypeId order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodGetStartDateOrganizationId(Long organizationId, String statusCode, Long financialPeriodTypeId);

    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriodTypeAssign fpa  join  fpa.financialPeriod fp where ((fp.startDate=:startDate and fpa.id=:typeAssignId and fp.financialPeriodType.id=:financialPeriodTypeId ) " +
            " or (fp.endDate =:endDate and fpa.id=:typeAssignId and fp.financialPeriodType.id=:financialPeriodTypeId )) and fp.financialPeriodStatus.id = 1  ")
    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime startDate, LocalDateTime endDate, Long typeAssignId, Long financialPeriodTypeId);

    @Query("select 1 from FinancialPeriodTypeAssign  fpa  join FinancialPeriod fp on  " +
            "fp.id = fpa.financialPeriod.id and fpa.organization.id =:organizationId  " +
            "where fp.financialPeriodStatus.id=1  and fp.endDate=" +
            "(select  fpd.startDate -1  from FinancialPeriod  fpd where fpd.id=:financialPeriodId) ")
    Long checkFinancialStatusIdIsOpen(Long financialPeriodId, Long organizationId);


    @Query("select 1 from FinancialPeriodTypeAssign  fpa  join FinancialPeriod fp on  " +
            "fp.id = fpa.financialPeriod.id and fpa.organization.id =:organizationId  " +
            "where fp.financialPeriodStatus.id=2  and fp.startDate=" +
            "(select  fpd.endDate +1  from FinancialPeriod  fpd where fpd.id=:financialPeriodId) ")
    Long checkFinancialStatusIdIsClose(Long financialPeriodId, Long organizationId);

}

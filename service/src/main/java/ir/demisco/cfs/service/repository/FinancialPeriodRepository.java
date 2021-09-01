package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {

    @Query(value = "select fp from  FinancialPeriod fp join  fp.financialPeriodTypeAssign fpa join  fp.financialPeriodStatus fps" +
            " where fpa.organization.id=:organizationId and fps.code =:statusCode and fpa.activeFlag=1 order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodTypeAssignOrganizationId(Long organizationId, String statusCode);

    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriod fp where " +
            " fp.endDate =:endDate and fp.financialPeriodTypeAssign.id=:typeAssignId  ")
    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime endDate, Long typeAssignId);

    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriod fp where ((fp.startDate=:startDate and fp.financialPeriodTypeAssign.id=:typeAssignId ) " +
            " or (fp.endDate =:endDate and fp.financialPeriodTypeAssign.id=:typeAssignId)) and fp.financialPeriodStatus.id = 1  ")
    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime startDate, LocalDateTime endDate, Long typeAssignId);

    @Query(value = "select fp from  FinancialPeriod fp join  fp.financialPeriodTypeAssign fpa join  fp.financialPeriodStatus fps" +
            " where fpa.organization.id=:organizationId and fpa.activeFlag=1 order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodGetStartDateOrganizationId(Long organizationId);


    @Query("select fnpr from FinancialPeriod fnpr " +
            " where  fnpr.deletedDat is null  " +
            "  and fnpr.financialPeriodTypeAssign.id in (select fpt.id" +
            "      from FinancialPeriodTypeAssign fpt" +
            "     where fpt.organization.id =:organizationId  And fpt.activeFlag=1 " +
            "     and fpt.deletedDat is null)")
    List<FinancialPeriod> findActiveFinancialPeriod(Long organizationId);

    @Query(value = "select" +
            "        fp.id," +
            "        ' دوره مالی از ' || case          " +
            "            when fpty.calendar_type_id = 1 then           TO_CHAR(TO_DATE(TO_char(fp.start_date," +
            "            'mm/dd/yyyy')," +
            "            'mm/dd/yyyy')," +
            "            'yyyy/mm/dd'," +
            "            'NLS_CALENDAR=persian') || ' تا ' ||          TO_CHAR(TO_DATE(TO_char(fp.end_date," +
            "            'mm/dd/yyyy')," +
            "            'mm/dd/yyyy')," +
            "            'yyyy/mm/dd'," +
            "            'NLS_CALENDAR=persian')         " +
            "            when fpty.calendar_type_id = 2 then          TO_CHAR(TO_DATE(TO_char(fp.start_date," +
            "            'mm/dd/yyyy')," +
            "            'mm/dd/yyyy')," +
            "            'yyyy/mm/dd') || ' تا ' ||          TO_CHAR(TO_DATE(TO_char(fp.end_date," +
            "            'mm/dd/yyyy')," +
            "            'mm/dd/yyyy')," +
            "            'yyyy/mm/dd')            " +
            "        end description   " +
            "    from" +
            "        fnpr.financial_period fp " +
            "    inner join" +
            "        fnpr.financial_period_type_assign fpt    " +
            "            on fp.finan_period_type_assign_id = fpt.id   " +
            "            and fpt.organization_id = :organizationId " +
            "            and fpt.deleted_date is null   " +
            "            and fpt.active_flag = 1 " +
            "    inner join" +
            "        fnpr.financial_period_type fpty    " +
            "            on fpt.financial_period_type_id = fpty.id " +
            "    where" +
            "        fp.financial_period_status_id = 1   " +
            "        and to_date(:localDate, 'yyyy-mm-dd') between fp.start_date and fp.end_date   " +
            "        and fp.deleted_date is null "
            , nativeQuery = true)
    List<Object[]> findByFinancialPeriodAndDate(String localDate,Long organizationId);
}

package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {

    @Query(value = " SELECT nvl(max(to_number(t.code)),10) +1 " +
            "from fnpr.financial_period t " +
            "inner join fnpr.financial_period_type_assign t1 on t.id = t1.financial_period_id " +
            "and t1.organization_id = :organizationId  " +
            "And t1.active_flag=1 " +
            "And t1.deleted_date is null " +
            " where t.deleted_date is null "
            , nativeQuery = true)
    String getCodeFinancialPeriod(Long organizationId);

    @Query(value = " SELECT ('دوره مالی منتهی به ' || TO_CHAR(TO_DATE(:localDate , 'yyyy-mm-dd'), 'yyyy/mm/dd', " +
            "                  'NLS_CALENDAR=persian')) " +
            "                   from dual  "
            , nativeQuery = true)
    String getDescriptionFinancialPeriod(String localDate);

//    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriod fp where ((fp.startDate=:startDate and fp.financialPeriodTypeAssign.id=:typeAssignId and fp.financialPeriodType.id=:financialPeriodTypeId ) " +
//            " or (fp.endDate =:endDate and fp.financialPeriodTypeAssign.id=:typeAssignId and fp.financialPeriodType.id=:financialPeriodTypeId )) and fp.financialPeriodStatus.id = 1  ")
//    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime startDate, LocalDateTime endDate, Long typeAssignId, Long financialPeriodTypeId);


//    @Query(value = "select fp from  FinancialPeriod fp left outer join  fp.financialPeriodTypeAssign fpa join  fp.financialPeriodStatus fps left outer join fp.financialPeriodType fpt" +
//            " where fpa.organization.id=:organizationId and  fps.code =:statusCode and fpa.activeFlag=1 and fpt.id=:financialPeriodTypeId order by fp.endDate desc ")
//    List<FinancialPeriod> findByFinancialPeriodGetStartDateOrganizationId(Long organizationId, String statusCode, Long financialPeriodTypeId);


//    @Query("select fnpr from FinancialPeriod fnpr " +
//            " where  fnpr.deletedDate is null  " +
//            "  and fnpr.financialPeriodTypeAssign.id in (select fpt.id" +
//            "      from FinancialPeriodTypeAssign fpt" +
//            "     where fpt.organization.id =:organizationId  And fpt.activeFlag=1 " +
//            "     and fpt.deletedDate is null)")
//    List<FinancialPeriod> findActiveFinancialPeriod(Long organizationId);

    @Query(value = "select fp.id, " +
            "       ' دوره مالی از ' || case" +
            "         when fpty.calendar_type_id = 1 then " +
            "          TO_CHAR(TO_DATE(TO_char(fp.start_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd', " +
            "                  'NLS_CALENDAR=persian') || ' تا ' || " +
            "          TO_CHAR(TO_DATE(TO_char(fp.end_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd'," +
            "                  'NLS_CALENDAR=persian') " +
            "         when fpty.calendar_type_id = 2 then " +
            "          TO_CHAR(TO_DATE(TO_char(fp.start_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd') || ' تا ' || " +
            "          TO_CHAR(TO_DATE(TO_char(fp.end_date, 'mm/dd/yyyy'), 'mm/dd/yyyy'), " +
            "                  'yyyy/mm/dd') " +
            "       end Full_description , " +
            "       fp.description , " +
            "       fp.code  " +
            "    from " +
            "        fnpr.financial_period fp " +
            "    inner join" +
            "        fnpr.financial_period_type_assign fpt    " +
            "            on fp.finan_period_type_assign_id = fpt.id   " +
            "            and fpt.organization_id = :organizationId " +
            "            and fpt.deleted_date is null   " +
            "            and fpt.active_flag = 1 " +
            "    inner join" +
            "        fnpr.financial_period_type fpty    " +
            "            on fp.financial_period_type_id  = fpty.id " +
            "    where " +
            "        fp.financial_period_status_id = 1   " +
            "        and to_date(:localDate, 'yyyy-mm-dd') between fp.start_date and fp.end_date " +
            " and  ( :financialPeriodType is null or fpty.id = :financialPeriodTypeId)" +
            "        and fp.deleted_date is null "
            , nativeQuery = true)
    List<Object[]> findByFinancialPeriodAndDate(String localDate, Long organizationId, Object financialPeriodType, Long financialPeriodTypeId);

//    @Query("select 1 from FinancialPeriod  fp  join FinancialPeriodTypeAssign ta on  " +
//            "ta.id = fp.financialPeriodTypeAssign.id and ta.organization.id =:organizationId  " +
//            "where fp.financialPeriodStatus.id=1  and fp.endDate=" +
//            "(select  fpd.startDate -1  from FinancialPeriod  fpd where fpd.id=:financialPeriodId) ")
//    Long checkFinancialStatusIdIsOpen(Long financialPeriodId, Long organizationId);

//    @Query("select 1 from FinancialPeriod  fp  join FinancialPeriodTypeAssign ta on  " +
//            "ta.id = fp.financialPeriodTypeAssign.id and ta.organization.id =:organizationId  " +
//            "where fp.financialPeriodStatus.id=2  and fp.startDate=" +
//            "(select  fpd.endDate +1  from FinancialPeriod  fpd where fpd.id=:financialPeriodId) ")
//    Long checkFinancialStatusIdIsClose(Long financialPeriodId, Long organizationId);

//    @Query("select min(fp.startDate) from FinancialPeriod  fp  join fp.financialPeriodTypeAssign fpa  " +
//            " join fp.financialPeriodType fpt " +
//            " where fp.deletedDate is null and fp.financialPeriodStatus.id=1 " +
//            " and fpa.organization.id =:organizationId " +
//            " and fpa.deletedDate is null and fpa.activeFlag=1 ")

    @Query(value = "SELECT MIN(FP.START_DATE) as FinancialPeriodStartDate" +
            "  FROM FNPR.FINANCIAL_PERIOD FP" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN FPT" +
            "    ON FP.FINAN_PERIOD_TYPE_ASSIGN_ID = FPT.ID" +
            "   AND FPT.ORGANIZATION_ID = :organizationId" +
            "   AND FPT.ACTIVE_FLAG = 1" +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FPTY" +
            "    ON FP.FINANCIAL_PERIOD_TYPE_ID = FPTY.ID" +
            " WHERE FP.FINANCIAL_PERIOD_STATUS_ID = 1" +
            " and  ( :financialPeriodType is null or FPTY.Id = :financialPeriodTypeId)"
            , nativeQuery = true)
    LocalDateTime findByFinancialPeriodAndOrganizationId(Long organizationId, Object financialPeriodType, Long financialPeriodTypeId);

    @Query(value = "SELECT  CASE " +
            "         WHEN T.FINANCIAL_PERIOD_STATUS_ID = 2 THEN " +
            "          0 " +
            "         ELSE " +
            "          1 " +
            "       END " +
            "  FROM FNPR.FINANCIAL_PERIOD T" +
            " WHERE T.ID = :financialPeriodId " +
            " and t.deleted_date is null "
            , nativeQuery = true)
    Long findFinancialPeriodById(Long financialPeriodId);

    @Query(value = " SELECT CASE " +
            "         WHEN FMN.FINANCIAL_MONTH_STATUS_ID = 2 THEN " +
            "          0 " +
            "         ELSE " +
            "          1 " +
            "       END " +
            "  FROM FNPR.FINANCIAL_MONTH FMN " +
            " INNER JOIN FNPR.FINANCIAL_MONTH_TYPE FMT " +
            "    ON FMT.ID = FMN.FINANCIAL_MONTH_TYPE_ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE FNP " +
            "    ON FNP.ID = FMT.FINANCIAL_PERIOD_TYPE_ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN PT " +
            "    ON PT.FINANCIAL_PERIOD_TYPE_ID = FNP.ID " +
            " INNER JOIN fnpr.FINANCIAL_PERIOD FP " +
            "   ON FP.FINANCIAL_PERIOD_TYPE_ID = fnp.id " +
            "   AND FMN.FINANCIAL_PERIOD_ID = FP.ID " +
            " WHERE FP.ID = :financialPeriodId " +
            "   AND (CASE CALENDAR_TYPE_ID " +
            "         WHEN 2 THEN " +
            "          EXTRACT(MONTH FROM " +
            "                  to_date(:date, 'mm/dd/yyyy')) " +
            "         WHEN 1 THEN " +
            "          TO_NUMBER(SUBSTR(TO_CHAR(to_date(:date, 'mm/dd/yyyy')," +
            "                                   'yyyy/mm/dd'," +
            "                                   'NLS_CALENDAR=persian')," +
            "                           6," +
            "                           2))" +
            "       END = CASE " +
            "         WHEN FNP.CURRENT_YEAR_FLAG = 1 THEN " +
            "          FNP.FROM_MONTH + FMT.MONTH_NUMBER - 1 " +
            "         ELSE " +
            "          CASE " +
            "            WHEN FNP.FROM_MONTH + FMT.MONTH_NUMBER - 1 > 12 THEN " +
            "             FNP.FROM_MONTH + FMT.MONTH_NUMBER - 13 " +
            "            ELSE " +
            "             FNP.FROM_MONTH + FMT.MONTH_NUMBER - 1 " +
            "          END" +
            "       END AND FMN.FINANCIAL_MONTH_STATUS_ID = 1) "
            , nativeQuery = true)
    Long findFinancialPeriodByFinancialPeriodIdAndDate(Long financialPeriodId, String date);
}

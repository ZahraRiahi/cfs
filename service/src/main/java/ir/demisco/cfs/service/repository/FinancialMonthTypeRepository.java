package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialMonthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialMonthTypeRepository extends JpaRepository<FinancialMonthType,Long> {

@Query(value = " select TO_DATE((select case calendar_type_id" +
        "                         when 1 then " +
        "                          min(ad.pdat_ggdate_c) " +
        "                         when 2 then" +
        "                          min(ad.pdat_ggdate_c) " +
        "                       end " +
        "                  from clnd.all_date ad " +
        "                 where (calendar_type_id = 1 and" +
        "                       ad.pdat_hsdate_yer =" +
        "                       substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy')," +
        "                                       'yyyy/mm/dd'," +
        "                                       'NLS_CALENDAR=persian')," +
        "                               0," +
        "                               4) And" +
        "                       ad.pdat_hsdate_mon =" +
        "                       substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy')," +
        "                                       'yyyy/mm/dd'," +
        "                                       'NLS_CALENDAR=persian')," +
        "                               6," +
        "                               2))" +
        "                    or " +
        "                       (calendar_type_id = 2 and " +
        "                       ad.pdat_ggdate_yer =" +
        "                       extract(year from" +
        "                                TO_DATE(start_date_final, 'mm/dd/yyyy')) and " +
        "                       ad.pdat_ggdate_mon =" +
        "                       extract(month from" +
        "                                TO_DATE(start_date_final, 'mm/dd/yyyy')))" +
        "                ), 'yyyy/mm/dd')  start_date," +
        "              TO_DATE( (select" +
        "                      TO_CHAR(TO_DATE( max(ad.pdat_hsdate_c), 'yyyy/mm/dd','NLS_CALENDAR=persian')," +
        "                                       'yyyy/mm/dd'" +
        "                                       )" +
        "                  from clnd.all_date ad" +
        "                 where (calendar_type_id = 1 and" +
        "                       ad.pdat_hsdate_yer =" +
        "                       substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy')," +
        "                                       'yyyy/mm/dd'," +
        "                                       'NLS_CALENDAR=persian')," +
        "                               0," +
        "                               4) And" +
        "                       ad.pdat_hsdate_mon =" +
        "                       substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy')," +
        "                                       'yyyy/mm/dd'," +
        "                                       'NLS_CALENDAR=persian')," +
        "                               6," +
        "                               2))" +
        "                    or " +
        "                       (calendar_type_id = 2 and" +
        "                       ad.pdat_ggdate_yer =" +
        "                       extract(year from" +
        "                                TO_DATE(start_date_final, 'mm/dd/yyyy')) and" +
        "                       ad.pdat_ggdate_mon =" +
        "                       extract(month from" +
        "                                TO_DATE(start_date_final, 'mm/dd/yyyy')))" +
        "                ), 'yyyy/mm/dd') end_date," +
        "               MONTH_TYPE_ID," +
        "               CALENDAR_TYPE_ID," +
        "               description || ' ' ||" +
        "               case calendar_type_id" +
        "                 when 1 then" +
        "                  substr(TO_CHAR(TO_DATE(start_date_final, 'mm/dd/yyyy')," +
        "                                 'yyyy/mm/dd'," +
        "                                 'NLS_CALENDAR=persian')," +
        "                         0," +
        "                         4)" +
        "                 when 2 then " +
        "                  TO_CHAR(extract(year from" +
        "                                  TO_DATE(start_date_final, 'mm/dd/yyyy')))" +
        "               End des " +
        "          from (SELECT FM.DESCRIPTION," +
        "                       FM.MONTH_NUMBER," +
        "                       FM.ID MONTH_TYPE_ID," +
        "                       FNP.CALENDAR_TYPE_ID," +
        "                       case " +
        "                         when FNP.CALENDAR_TYPE_ID = 1 then" +
        "                          (select TO_CHAR(TO_DATE(TO_CHAR(dt_2.pdat_ggdate_d," +
        "                                                          'mm/dd/yyyy')," +
        "                                                  'mm/dd/yyyy')," +
        "                                          'mm/dd/yyyy')" +
        "                             from clnd.all_date dt" +
        "                            inner join clnd.all_date dt_2" +
        "                               on dt_2.pdat_hsdate_mon =" +
        "                                  dt.pdat_hsdate_mon + FM.MONTH_NUMBER - 1" +
        "                              and dt_2.pdat_hsdate_day = dt.pdat_hsdate_day" +
        "                              and dt_2.pdat_hsdate_yer = dt.pdat_hsdate_yer" +
        "                            where dt.pdat_ggdate_d = fp.start_date)" +
        "                         when FNP.CALENDAR_TYPE_ID = 2 then" +
        "                          TO_CHAR(TO_DATE(TO_CHAR(ADD_MONTHS((FP.START_DATE + 1)," +
        "                                                             MONTH_NUMBER - 1)," +
        "                                                  'mm/dd/yyyy')," +
        "                                          'mm/dd/yyyy') - 1," +
        "                                  'mm/dd/yyyy')" +
        "                       end " +
        "                       START_DATE_FINAL," +
        "                       CASE" +
        "                        (SELECT DISTINCT 1" +
        "                           FROM FNPR.FINANCIAL_MONTH FNM" +
        "                          WHERE FNM.FINANCIAL_PERIOD_ID = :financialPeriodId" +
        "                            AND FNM.DELETED_DATE IS NULL)" +
        "                         WHEN 1 THEN" +
        "                          (SELECT MAX(FNM.END_DATE) + 1" +
        "                             FROM FNPR.FINANCIAL_MONTH FNM" +
        "                            WHERE FNM.FINANCIAL_PERIOD_ID = :financialPeriodId " +
        "                              AND FNM.DELETED_DATE IS NULL)" +
        "                         ELSE" +
        "                          (SELECT FP.START_DATE" +
        "                             FROM FNPR.FINANCIAL_PERIOD FP" +
        "                            WHERE FP.ID = :financialPeriodId " +
        "                              AND FP.DELETED_DATE IS NULL)" +
        "                       END START_DATE" +
        "                  FROM FNPR.FINANCIAL_PERIOD_TYPE FNP" +
        "                 INNER JOIN FNPR.FINANCIAL_PERIOD FP" +
        "                    ON FP.FINANCIAL_PERIOD_TYPE_ID = FNP.ID" +
        "                   AND FP.ID = :financialPeriodId " +
        "                 INNER JOIN FNPR.FINANCIAL_MONTH_TYPE FM" +
        "                    ON FNP.ID = FM.FINANCIAL_PERIOD_TYPE_ID" +
        "                 INNER JOIN FNPR.FINANCIAL_PERIOD_TYPE_ASSIGN TA" +
        "                    ON TA.FINANCIAL_PERIOD_ID = FP.ID" +
        "                   AND TA.ACTIVE_FLAG = 1" +
        "                   AND TA.ORGANIZATION_ID = :organizationId ) ", nativeQuery = true)
List<Object[]> findByParam(Long financialPeriodId,Long organizationId);
}

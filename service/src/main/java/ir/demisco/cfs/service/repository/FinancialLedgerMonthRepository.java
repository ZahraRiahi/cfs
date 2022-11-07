package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FinancialLedgerMonthRepository extends JpaRepository<FinancialLedgerMonth, Long> {

    @Query(value = " SELECT 1 " +
            "  FROM fndc.FINANCIAL_LEDGER_MONTH T " +
            " INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
            "    ON LP.FINANCIAL_PERIOD_ID = :financialPeriodId " +
            "   AND LP.ID = T.FINANCIAL_LEDGER_PERIOD_ID " +
            " INNER JOIN FNPR.FINANCIAL_PERIOD FP " +
            "    ON FP.ID = LP.FINANCIAL_PERIOD_ID " +
            " WHERE T.FIN_LEDGER_MONTH_STAT_ID = 1 " +
            " GROUP BY FINANCIAL_LEDGER_PERIOD_ID, FP.OPEN_MONTH_COUNT " +
            "HAVING COUNT(*) > :openMonthCount  ", nativeQuery = true)
   Long findByFinancialPeriodId(Long financialPeriodId,Long openMonthCount);

}

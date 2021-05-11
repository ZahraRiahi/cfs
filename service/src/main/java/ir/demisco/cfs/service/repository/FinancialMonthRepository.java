package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancialMonthRepository extends JpaRepository<FinancialMonth, Long> {

//    @Query(value = "select coalesce(count(fp.id),0) from  FinancialMonth fm join fm.financialPeriod fp " +
//            " join  fp.financialPeriodStatus fps " +
//            " where fm.id=:financialMonthId and fps.code =:statusCode  ")
//    Long findByFinancialMonthIdAndStatus(Long financialMonthId, String statusCode);

}

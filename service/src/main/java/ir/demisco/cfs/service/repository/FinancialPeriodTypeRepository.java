package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialPeriodTypeRepository extends JpaRepository<FinancialPeriodType, Long> {
    @Query(value = "select fpt from  FinancialPeriodType fpt  where fpt.deletedDate is null")
    List<FinancialPeriodType> findByFinancialPeriodType();
}

package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialMonthRepository extends JpaRepository<FinancialMonth, Long> {

}

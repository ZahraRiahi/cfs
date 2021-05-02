package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPeriodStatusRepository extends JpaRepository<FinancialPeriodStatus,Long> {
}

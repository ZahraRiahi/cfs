package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialPeriodTypeAssignRepository extends JpaRepository<FinancialPeriodTypeAssign, Long> {
    @Query("select fpta from FinancialPeriodTypeAssign fpta where fpta.organization.id=:organizationId")
    List<FinancialPeriodTypeAssign> findByOrganizationId(Long organizationId);
}

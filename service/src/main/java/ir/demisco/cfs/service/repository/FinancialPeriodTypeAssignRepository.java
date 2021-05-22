package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodTypeAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FinancialPeriodTypeAssignRepository extends JpaRepository<FinancialPeriodTypeAssign, Long> {
    @Query("select fpta from FinancialPeriodTypeAssign fpta where fpta.organization.id=:organizationId")
    List<FinancialPeriodTypeAssign> findByOrganizationId(Long organizationId);

    @Query("select fpa from FinancialPeriodTypeAssign fpa where fpa.activeFlag=1 and fpa.organization.id=:organizationId ")
    Optional<FinancialPeriodTypeAssign> getFinancialPeriodTypeAssignId(Long organizationId);
}

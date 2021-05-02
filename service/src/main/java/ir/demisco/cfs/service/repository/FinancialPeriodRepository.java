package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod,Long> {

    @Query(value = "select fp from FinancialPeriod fp join fp.financialPeriodTypeAssign fpta where fpta.organization.id =:organizationId order by fp.startDate desc"
    ,countQuery = "select COUNT(fp.id) from FinancialPeriod fp join fp.financialPeriodTypeAssign fpta where fpta.organization.id =:organizationId")
    List<FinancialPeriod> findByFinancialPeriodTypeAssignOrganizationId(Long organizationId);
}

package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {

    @Query(value = "select fp from  FinancialPeriod fp join  fp.financialPeriodTypeAssign fpa join  fp.financialPeriodStatus fps" +
            " where fpa.organization.id=:organizationId and fps.code =:statusCode order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodTypeAssignOrganizationId(Long organizationId, String statusCode);

    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriod fp where " +
            " fp.endDate =:endDate and fp.financialPeriodTypeAssign.id=:typeAssignId  ")
    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime endDate, Long typeAssignId);

}

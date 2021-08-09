package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {

    @Query(value = "select fp from  FinancialPeriod fp join  fp.financialPeriodTypeAssign fpa join  fp.financialPeriodStatus fps" +
            " where fpa.organization.id=:organizationId and fps.code =:statusCode and fpa.activeFlag=1 order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodTypeAssignOrganizationId(Long organizationId, String statusCode);

    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriod fp where " +
            " fp.endDate =:endDate and fp.financialPeriodTypeAssign.id=:typeAssignId  ")
    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime endDate, Long typeAssignId);

    @Query("select coalesce(COUNT(fp.id),0) from FinancialPeriod fp where ((fp.startDate=:startDate and fp.financialPeriodTypeAssign.id=:typeAssignId ) " +
            " or (fp.endDate =:endDate and fp.financialPeriodTypeAssign.id=:typeAssignId)) and fp.financialPeriodStatus.id = 1  ")
    Long getCountByStartDateAndEndDateAndFinancialPeriodTypeAssignId(LocalDateTime startDate, LocalDateTime endDate, Long typeAssignId);

    @Query(value = "select fp from  FinancialPeriod fp join  fp.financialPeriodTypeAssign fpa join  fp.financialPeriodStatus fps" +
            " where fpa.organization.id=:organizationId and fpa.activeFlag=1 order by fp.endDate desc ")
    List<FinancialPeriod> findByFinancialPeriodGetStartDateOrganizationId(Long organizationId);


    @Query("select fnpr from FinancialPeriod fnpr " +
            " where  fnpr.deletedDat is null  " +
            "  and fnpr.financialPeriodTypeAssign.id in (select fpt.id" +
            "      from FinancialPeriodTypeAssign fpt" +
            "     where fpt.organization.id =:organizationId  And fpt.activeFlag=1 " +
            "     and fpt.deletedDat is null)")
    List<FinancialPeriod>  findActiveFinancialPeriod(Long organizationId);
    }

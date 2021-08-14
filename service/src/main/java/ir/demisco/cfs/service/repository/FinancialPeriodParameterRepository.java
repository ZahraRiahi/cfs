package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialPeriodParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialPeriodParameterRepository extends JpaRepository<FinancialPeriodParameter, Long> {

    @Query(value="  select " +
            "   financial_period_id, " +
            "   start_date, " +
            "   tax_deduction_rate, " +
            "   vat_tax_rate, " +
            "   vat_toll_rate, " +
            "   insurance_deduction_rate, " +
            "   max_fewer_amount, " +
            "   vat_fill_flag " +
            "    from fnpr.financial_period_parameter p " +
            "   where p.financial_period_id = " +
            "         (select id " +
            "            from (select t.id " +
            "                    from fnpr.financial_period t " +
            "                   where t.finan_period_type_assign_id = " +
            "                         (select id " +
            "                            from fnpr.financial_period_type_assign fpt " +
            "                           where organization_id = :organizationId " +
            "                             And active_flag = 1 " +
            "                             And fpt.deleted_date is null ) " +
            "                     and t.id != :periodId" +
            "                     And t.deleted_date is null " +
            "                   order by t.start_date desc) " +
            "           where rownum = 1 ) " +
            "           And p.deleted_date is null",nativeQuery = true)
    List<Object[]>  getPeriodParameterByPeriodId(Long organizationId,Long periodId);
}

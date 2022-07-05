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
            "   where p.financial_period_id =" +
            "       (select id" +
            "          from (select t.id" +
            "                  from fnpr.financial_period t" +
            "                 inner join fnpr.financial_period_type_assign tt" +
            "                    on t.id = tt.financial_period_id" +
            "                 where tt.organization_id = :organizationId " +
            "                   And tt.active_flag = 1" +
            "                   and t.id != :periodId" +
            "                 order by t.start_date desc)" +
            "         where rownum = 1)  ",nativeQuery = true)
    List<Object[]>  getPeriodParameterByPeriodId(Long organizationId,Long periodId);
}

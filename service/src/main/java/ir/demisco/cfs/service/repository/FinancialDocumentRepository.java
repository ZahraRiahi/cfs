package ir.demisco.cfs.service.repository;

import ir.demisco.cfs.model.entity.FinancialDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FinancialDocumentRepository extends JpaRepository<FinancialDocument, Long> {

    @Query("select fd.financialPeriod.id,fd.documentDate from FinancialDocument  fd " +
            " where fd.id=:financialDocumentId and fd.deletedDate is null")
    List<Object[]> financialDocumentById(Long financialDocumentId);

}

package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeResponse;
import ir.demisco.cfs.service.api.FinancialPeriodTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-financialPeriodType")
public class FinancialPeriodType {
    private final FinancialPeriodTypeService financialPeriodTypeService;

    public FinancialPeriodType(FinancialPeriodTypeService financialPeriodTypeService) {
        this.financialPeriodTypeService = financialPeriodTypeService;
    }

    @GetMapping("/Get")
    public ResponseEntity<List<FinancialPeriodTypeResponse>> responseEntity() {
        return ResponseEntity.ok(financialPeriodTypeService.getFinancialPeriodType());
    }

}

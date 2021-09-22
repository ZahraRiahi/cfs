package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialMonthDto;
import ir.demisco.cfs.service.api.FinancialMonthService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialMonth")
public class FinancialMonthController {
    private final FinancialMonthService financialMonthService;

    public FinancialMonthController(FinancialMonthService financialMonthService) {
        this.financialMonthService = financialMonthService;
    }

    @PostMapping("/list/{id}")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest, @PathVariable("id") Long financialPeriodId) {
        return ResponseEntity.ok(financialMonthService.getFinancialMonthByFinancialPeriodId(financialPeriodId, dataSourceRequest));
    }

    @PostMapping("/SetStatus/{id}")
    public ResponseEntity<FinancialMonthDto> changeStatusFinancialMonth(@PathVariable("id") Long financialMonthId) {
        return ResponseEntity.ok(financialMonthService.changeStatusFinancialMonthById(financialMonthId));
    }
}
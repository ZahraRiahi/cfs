package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financial")
public class FinancialPeriodController {

    private final FinancialPeriodService financialPeriodService;

    public FinancialPeriodController(FinancialPeriodService financialPeriodService) {
        this.financialPeriodService = financialPeriodService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialPeriodService.getFinancialPeriodByOrganizationId(2L, dataSourceRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialPeriodDto> saveFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
        Long aLong = financialPeriodService.save(financialPeriodDto);
        financialPeriodDto.setId(aLong);
        return ResponseEntity.ok(financialPeriodDto);
    }

    @PutMapping("/update")
    public ResponseEntity<FinancialPeriodDto> updateFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
        return ResponseEntity.ok(financialPeriodService.update(financialPeriodDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FinancialPeriodDto> changeStatusFinancialPeriod(@PathVariable("id") Long financialPeriodId) {
        return ResponseEntity.ok(null);
    }
}
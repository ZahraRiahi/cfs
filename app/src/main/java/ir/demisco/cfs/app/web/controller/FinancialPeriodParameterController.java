package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialPeriodParameterDto;
import ir.demisco.cfs.service.api.FinancialPeriodParameterService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialPeriodParameter")
public class FinancialPeriodParameterController {
    private final FinancialPeriodParameterService financialPeriodParameterService;

    public FinancialPeriodParameterController(FinancialPeriodParameterService financialPeriodParameterService) {
        this.financialPeriodParameterService = financialPeriodParameterService;
    }

    @PostMapping("/list/{id}")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest, @PathVariable("id") Long financialPeriodId) {
        return ResponseEntity.ok(financialPeriodParameterService.getFinancialPeriodParameterByFinancialPeriodId(financialPeriodId, dataSourceRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialPeriodParameterDto> saveFinancialPeriodParameter(@RequestBody FinancialPeriodParameterDto financialPeriodParameterDto) {
        Long aLong = financialPeriodParameterService.save(financialPeriodParameterDto);
        financialPeriodParameterDto.setId(aLong);
        return ResponseEntity.ok(financialPeriodParameterDto);
    }

}

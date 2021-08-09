package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDateDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialPeriod")
public class FinancialPeriodController {

    private final FinancialPeriodService financialPeriodService;

    public FinancialPeriodController(FinancialPeriodService financialPeriodService) {
        this.financialPeriodService = financialPeriodService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialPeriodService.getFinancialPeriodByOrganizationId(1L, dataSourceRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialPeriodDto> saveFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
        if (financialPeriodDto.getId() == null) {
            Long aLong = financialPeriodService.save(financialPeriodDto);
            financialPeriodDto.setId(aLong);
            return ResponseEntity.ok(financialPeriodDto);
        } else {
            return ResponseEntity.ok(financialPeriodService.update(financialPeriodDto));
        }
    }

    @PostMapping("/SetStatus")
    public ResponseEntity<FinancialPeriodDto> changeStatusFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
        return ResponseEntity.ok(financialPeriodService.changeStatusFinancialPeriodById(financialPeriodDto));
    }

    @PostMapping("/GetDate/{organizationId}")
    public ResponseEntity<FinancialPeriodDateDto> responseEntitygetStartDate(@PathVariable Long organizationId) {
        return ResponseEntity.ok(financialPeriodService.getStartDateFinancialPeriod(organizationId));
    }
}
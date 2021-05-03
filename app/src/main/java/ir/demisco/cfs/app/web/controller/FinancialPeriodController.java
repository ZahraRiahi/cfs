package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-test")
public class FinancialPeriodController {

    private final FinancialPeriodService financialPeriodService;

    public FinancialPeriodController(FinancialPeriodService financialPeriodService) {
        this.financialPeriodService = financialPeriodService;
    }


    @PostMapping()
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
//        SecurityHelper.getCurrentUser().getOrganizationId()
        return ResponseEntity.ok(financialPeriodService.getFinancialPeriodByOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId(), dataSourceRequest));
    }

//    @PostMapping()
//    public ResponseEntity<FinancialPeriodDto>  saveFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
//        financialPeriodService.save(financialPeriodDto);
//        return ResponseEntity.ok(financialPeriodDto);
//    }
}
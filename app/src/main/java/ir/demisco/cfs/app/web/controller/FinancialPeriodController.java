package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDateDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodNewResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-financialPeriod")
public class FinancialPeriodController {

    private final FinancialPeriodService financialPeriodService;

    public FinancialPeriodController(FinancialPeriodService financialPeriodService) {
        this.financialPeriodService = financialPeriodService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialPeriodService.getFinancialPeriodByOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId(), dataSourceRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialPeriodDto> saveFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
        if (financialPeriodDto.getId() == null) {
            FinancialPeriodDto financialPeriodDtoRes = financialPeriodService.save(financialPeriodDto);
            return ResponseEntity.ok(financialPeriodDtoRes);
        } else {
            return ResponseEntity.ok(financialPeriodService.update(financialPeriodDto));
        }
    }

    @PostMapping("/SetStatus")
    public ResponseEntity<FinancialPeriodDto> changeStatusFinancialPeriod(@RequestBody FinancialPeriodDto financialPeriodDto) {
        return ResponseEntity.ok(financialPeriodService.changeStatusFinancialPeriodById(financialPeriodDto));
    }

    @GetMapping("/GetDate")
    public ResponseEntity<FinancialPeriodDateDto> responseEntitygetStartDate() {
        return ResponseEntity.ok(financialPeriodService.getStartDateFinancialPeriod(SecurityHelper.getCurrentUser().getOrganizationId()));
    }

    @PostMapping("/GetCurrent")
    public ResponseEntity<List<FinancialPeriodResponse>> responseEntity(@RequestBody FinancialPeriodRequest financialPeriodRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialPeriodService.getFinancialAccountByDateAndOrgan(financialPeriodRequest, organizationId));
    }

    @GetMapping("/GetPeriodStartDate/{organizationId}")
    public ResponseEntity<FinancialPeriodNewResponse> responseEntity(@PathVariable Long organizationId) {
        return ResponseEntity.ok(financialPeriodService.getGetPeriodStartDateByOrganizationId(100L));

    }
}
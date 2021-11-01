package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialPeriodTypeAssignRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignSaveDto;
import ir.demisco.cfs.service.api.FinancialPeriodTypeAssignService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-financialPeriodTypeAssign")
public class FinancialPeriodTypeAssignController {
    private final FinancialPeriodTypeAssignService financialPeriodTypeAssignService;

    public FinancialPeriodTypeAssignController(FinancialPeriodTypeAssignService financialPeriodTypeAssignService) {
        this.financialPeriodTypeAssignService = financialPeriodTypeAssignService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<FinancialPeriodTypeAssignDto>> responseEntity() {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialPeriodTypeAssignService.getFinancialPeriodTypeAssignByOrganizationId(organizationId));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialPeriodTypeAssignSaveDto> saveFinancialPeriod(@RequestBody FinancialPeriodTypeAssignRequest financialPeriodTypeAssignRequest) {
        return ResponseEntity.ok(financialPeriodTypeAssignService.save(financialPeriodTypeAssignRequest));
    }
}

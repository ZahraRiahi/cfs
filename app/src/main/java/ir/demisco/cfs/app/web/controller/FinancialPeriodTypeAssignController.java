package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialPeriodTypeAssignDto;
import ir.demisco.cfs.service.api.FinancialPeriodTypeAssignService;
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
        return ResponseEntity.ok(financialPeriodTypeAssignService.getFinancialPeriodTypeAssignByOrganizationId(8L));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialPeriodTypeAssignDto> saveFinancialPeriod(@RequestBody FinancialPeriodTypeAssignDto financialPeriodTypeAssignDto) {
        Long aLong = financialPeriodTypeAssignService.save(financialPeriodTypeAssignDto);
        financialPeriodTypeAssignDto.setId(aLong);
        return ResponseEntity.ok(financialPeriodTypeAssignDto);
    }
}

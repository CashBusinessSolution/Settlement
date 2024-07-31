package com.hps.fee;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fee")
public class FeeController {
    private final FeeService feeService;

    @PostMapping
    public Fee createFees(@RequestBody Fee fee) {
        return feeService.createFees(fee);
    }

    @GetMapping
    public List<Fee> getAllFees() {
        return feeService.getAllFees();
    }

    @GetMapping("/{id}")
    public Fee getFeesById(@PathVariable Long id) {
        return feeService.getFeesById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFees(@PathVariable Long id) {
        feeService.deleteFees(id);
    }

    @PostMapping("/calculate")
    public Fee calculateFee(@RequestParam BigDecimal amount, @RequestParam String description) {
        // Utilisation du service pour calculer les frais
        return feeService.calculateFee(amount, description);
    }
}


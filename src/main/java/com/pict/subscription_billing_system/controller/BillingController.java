
package com.pict.subscription_billing_system.controller;

import com.pict.subscription_billing_system.entity.Billing;
import com.pict.subscription_billing_system.service.BillingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public List<Billing> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/{id}")
    public Billing getBillById(@PathVariable Long id) {
        return billingService.getBillById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Billing> getBillsByCustomer(
            @PathVariable Long customerId) {
        return billingService.getBillsByCustomer(customerId);
    }

    @PutMapping("/{id}/pay")
    public Billing markBillAsPaid(@PathVariable Long id) {
        return billingService.markBillAsPaid(id);
    }
}

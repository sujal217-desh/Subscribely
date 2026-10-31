
package com.pict.subscription_billing_system.service;

import com.pict.subscription_billing_system.entity.Billing;
import com.pict.subscription_billing_system.repository.BillingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    public BillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Billing getBillById(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Billing record not found with ID: " + id));
    }

    public List<Billing> getBillsByCustomer(Long customerId) {
        return billingRepository.findBySubscriptionCustomerId(customerId);
    }

    @Transactional
    public Billing markBillAsPaid(Long id) {
        Billing bill = getBillById(id);

        if ("PAID".equalsIgnoreCase(bill.getPaymentStatus())) {
            throw new IllegalArgumentException(
                    "This bill has already been paid");
        }

        bill.setPaymentStatus("PAID");

        return billingRepository.save(bill);
    }
}

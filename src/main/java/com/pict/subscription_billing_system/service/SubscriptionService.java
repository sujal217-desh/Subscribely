
package com.pict.subscription_billing_system.service;

import com.pict.subscription_billing_system.entity.Billing;
import com.pict.subscription_billing_system.entity.Customer;
import com.pict.subscription_billing_system.entity.Plan;
import com.pict.subscription_billing_system.entity.Subscription;
import com.pict.subscription_billing_system.repository.BillingRepository;
import com.pict.subscription_billing_system.repository.CustomerRepository;
import com.pict.subscription_billing_system.repository.PlanRepository;
import com.pict.subscription_billing_system.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;
    private final BillingRepository billingRepository;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            CustomerRepository customerRepository,
            PlanRepository planRepository,
            BillingRepository billingRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.planRepository = planRepository;
        this.billingRepository = billingRepository;
    }

    @Transactional
    public Subscription createSubscription(
            Long customerId, Long planId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer not found with ID: " + customerId));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Plan not found with ID: " + planId));

        LocalDate startDate = LocalDate.now();
        LocalDate endDate;

        if ("MONTHLY".equalsIgnoreCase(plan.getBillingCycle())) {
            endDate = startDate.plusMonths(1);
        } else if ("YEARLY".equalsIgnoreCase(plan.getBillingCycle())) {
            endDate = startDate.plusYears(1);
        } else {
            throw new IllegalArgumentException(
                    "Unsupported billing cycle");
        }

        Subscription subscription = new Subscription();
        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setStatus("ACTIVE");

        Subscription savedSubscription =
                subscriptionRepository.save(subscription);

        Billing billing = new Billing();
        billing.setSubscription(savedSubscription);
        billing.setInvoiceNumber(
                "INV-" + UUID.randomUUID()
                        .toString().substring(0, 8).toUpperCase());
        billing.setAmount(plan.getPrice());
        billing.setBillingDate(startDate);
        billing.setPaymentStatus("PENDING");

        billingRepository.save(billing);

        return savedSubscription;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Subscription not found with ID: " + id));
    }

    public List<Subscription> getSubscriptionsByCustomer(
            Long customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer not found with ID: " + customerId);
        }

        return subscriptionRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Subscription cancelSubscription(Long id) {

        Subscription subscription = getSubscriptionById(id);

        if ("CANCELLED".equalsIgnoreCase(subscription.getStatus())) {
            throw new IllegalArgumentException(
                    "Subscription is already cancelled");
        }

        subscription.setStatus("CANCELLED");

        return subscriptionRepository.save(subscription);
    }
}

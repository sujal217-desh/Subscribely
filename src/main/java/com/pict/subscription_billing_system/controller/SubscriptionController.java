
package com.pict.subscription_billing_system.controller;

import com.pict.subscription_billing_system.dto.CreateSubscriptionRequest;
import com.pict.subscription_billing_system.entity.Subscription;
import com.pict.subscription_billing_system.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public Subscription createSubscription(
            @Valid @RequestBody CreateSubscriptionRequest request) {
        return subscriptionService.createSubscription(
                request.getCustomerId(),
                request.getPlanId()
        );
    }

    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public Subscription getSubscriptionById(@PathVariable Long id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Subscription> getSubscriptionsByCustomer(
            @PathVariable Long customerId) {
        return subscriptionService.getSubscriptionsByCustomer(customerId);
    }

    @PutMapping("/{id}/cancel")
    public Subscription cancelSubscription(@PathVariable Long id) {
        return subscriptionService.cancelSubscription(id);
    }
}

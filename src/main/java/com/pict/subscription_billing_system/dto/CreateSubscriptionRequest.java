
package com.pict.subscription_billing_system.dto;

import jakarta.validation.constraints.NotNull;

public class CreateSubscriptionRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Plan ID is required")
    private Long planId;

    public CreateSubscriptionRequest() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}

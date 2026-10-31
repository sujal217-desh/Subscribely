
package com.pict.subscription_billing_system.repository;

import com.pict.subscription_billing_system.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findBySubscriptionCustomerId(Long customerId);
}

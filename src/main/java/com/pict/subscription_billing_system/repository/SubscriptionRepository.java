
package com.pict.subscription_billing_system.repository;

import com.pict.subscription_billing_system.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByCustomerId(Long customerId);
}

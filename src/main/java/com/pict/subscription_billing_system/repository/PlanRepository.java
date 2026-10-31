
package com.pict.subscription_billing_system.repository;

import com.pict.subscription_billing_system.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}


package com.pict.subscription_billing_system.service;

import com.pict.subscription_billing_system.entity.Plan;
import com.pict.subscription_billing_system.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan createPlan(Plan plan) {

        if (plan.getPrice() == null ||
                plan.getPrice().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Plan price must be greater than zero");
        }

        if (plan.getBillingCycle() == null ||
                !(plan.getBillingCycle().equalsIgnoreCase("MONTHLY")
                        || plan.getBillingCycle().equalsIgnoreCase("YEARLY"))) {
            throw new IllegalArgumentException(
                    "Billing cycle must be MONTHLY or YEARLY");
        }

        plan.setBillingCycle(plan.getBillingCycle().toUpperCase());

        return planRepository.save(plan);
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Plan not found with ID: " + id));
    }

    public Plan updatePlan(Long id, Plan updatedPlan) {

        Plan existingPlan = getPlanById(id);

        if (updatedPlan.getPrice() == null ||
                updatedPlan.getPrice().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Plan price must be greater than zero");
        }

        if (updatedPlan.getBillingCycle() == null ||
                !(updatedPlan.getBillingCycle().equalsIgnoreCase("MONTHLY")
                        || updatedPlan.getBillingCycle().equalsIgnoreCase("YEARLY"))) {
            throw new IllegalArgumentException(
                    "Billing cycle must be MONTHLY or YEARLY");
        }

        existingPlan.setName(updatedPlan.getName());
        existingPlan.setPrice(updatedPlan.getPrice());
        existingPlan.setBillingCycle(
                updatedPlan.getBillingCycle().toUpperCase());

        return planRepository.save(existingPlan);
    }

    public void deletePlan(Long id) {
        Plan plan = getPlanById(id);
        planRepository.delete(plan);
    }
}

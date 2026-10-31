
package com.pict.subscription_billing_system.controller;

import com.pict.subscription_billing_system.entity.Plan;
import com.pict.subscription_billing_system.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public Plan createPlan(@Valid @RequestBody Plan plan) {
        return planService.createPlan(plan);
    }

    @GetMapping
    public List<Plan> getAllPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("/{id}")
    public Plan getPlanById(@PathVariable Long id) {
        return planService.getPlanById(id);
    }

    @PutMapping("/{id}")
    public Plan updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody Plan plan) {
        return planService.updatePlan(id, plan);
    }

    @DeleteMapping("/{id}")
    public String deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return "Plan deleted successfully";
    }
}

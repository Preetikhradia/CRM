package com.Preeti.crm.controller;

import com.Preeti.crm.dto.DashboardStats;
import com.Preeti.crm.model.Lead;
import com.Preeti.crm.model.Sale;
import com.Preeti.crm.model.Task;
import com.Preeti.crm.repository.LeadRepository;
import com.Preeti.crm.repository.SaleRepository;
import com.Preeti.crm.repository.TaskRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class DashboardController {

    private final LeadRepository leadRepository;
    private final SaleRepository saleRepository;
    private final TaskRepository taskRepository;

    public DashboardController(LeadRepository leadRepository, SaleRepository saleRepository, TaskRepository taskRepository) {
        this.leadRepository = leadRepository;
        this.saleRepository = saleRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/stats")
    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();
        List<Sale> allSales = saleRepository.findAll();
        List<Lead> allLeads = leadRepository.findAll();
        List<Task> allTasks = taskRepository.findAll();

        // 1. Calculate Total Revenue (Only Closed-Won)
        double totalRevenue = allSales.stream()
                .filter(s -> "Closed-Won".equals(s.getStatus()))
                .mapToDouble(Sale::getAmount)
                .sum();
        stats.setTotalRevenue(totalRevenue);

        // 2. Calculate Active Leads (Not "Closed")
        long activeLeads = allLeads.size();
        stats.setActiveLeads(activeLeads);

        // 3. Sales Pipeline (Money in Proposal/Negotiation)
        double pipeline = allSales.stream()
                .filter(s -> "Proposal".equals(s.getStatus()) || "Negotiation".equals(s.getStatus()))
                .mapToDouble(Sale::getAmount)
                .sum();
        stats.setSalesPipelineValue(pipeline);

        // 4. Pending Tasks
        long pending = allTasks.stream()
                .filter(t -> !"Completed".equals(t.getStatus()))
                .count();
        stats.setPendingTasks(pending);

        // 5. Generate Chart Data (Group Sales by Customer for now)
        // In a real app, you would group by Month
        List<Map<String, Object>> chartData = new ArrayList<>();
        // Taking last 5 sales for the chart demo
        allSales.stream().limit(5).forEach(sale -> {
            Map<String, Object> point = new HashMap<>();
            point.put("name", sale.getCustomerName());
            point.put("sales", sale.getAmount());
            chartData.add(point);
        });
        stats.setRevenueData(chartData);

        // 6. Recent Activity Feed
        List<String> activities = new ArrayList<>();
        if (!allLeads.isEmpty()) activities.add("New Lead: " + allLeads.get(allLeads.size()-1).getName());
        if (!allSales.isEmpty()) activities.add("New Sale: " + allSales.get(allSales.size()-1).getCustomerName());
        if (!allTasks.isEmpty()) activities.add("New Task: " + allTasks.get(allTasks.size()-1).getTitle());
        stats.setRecentActivity(activities);

        return stats;
    }
}
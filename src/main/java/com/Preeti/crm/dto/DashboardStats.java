package com.Preeti.crm.dto;

import java.util.List;
import java.util.Map;

public class DashboardStats {
    private Double totalRevenue;
    private long activeLeads;
    private Double salesPipelineValue;
    private long pendingTasks;
    private List<Map<String, Object>> revenueData; // For the Chart
    private List<String> recentActivity; // For the Feed

    // Getters and Setters
    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }
    public long getActiveLeads() { return activeLeads; }
    public void setActiveLeads(long activeLeads) { this.activeLeads = activeLeads; }
    public Double getSalesPipelineValue() { return salesPipelineValue; }
    public void setSalesPipelineValue(Double salesPipelineValue) { this.salesPipelineValue = salesPipelineValue; }
    public long getPendingTasks() { return pendingTasks; }
    public void setPendingTasks(long pendingTasks) { this.pendingTasks = pendingTasks; }
    public List<Map<String, Object>> getRevenueData() { return revenueData; }
    public void setRevenueData(List<Map<String, Object>> revenueData) { this.revenueData = revenueData; }
    public List<String> getRecentActivity() { return recentActivity; }
    public void setRecentActivity(List<String> recentActivity) { this.recentActivity = recentActivity; }
}
package com.Preeti.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "sales")
public class Sale {
    @Id
    private String id;
    private String customerName;
    private Double amount;
    private String status; // Proposal, Negotiation, Closed-Won, Closed-Lost
    private LocalDate date;
    private String assignedRep;

    // Constructors, Getters, Setters
    public Sale() { this.date = LocalDate.now(); } // Default to today

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getAssignedRep() { return assignedRep; }
    public void setAssignedRep(String assignedRep) { this.assignedRep = assignedRep; }
}
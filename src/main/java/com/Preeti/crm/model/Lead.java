package com.Preeti.crm.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "leads")
public class Lead {

    @Id
    private String id;

    private String name;
    private String email;
    private String phone;

    private String source;   // Referral, Ads, Web
    private String status;   // NEW, CONTACTED, CONVERTED, LOST

    // Assigned sales rep (email or userId)
    private String assignedSalesRep;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedSalesRep() {
        return assignedSalesRep;
    }

    public void setAssignedSalesRep(String assignedSalesRep) {
        this.assignedSalesRep = assignedSalesRep;
    }
}

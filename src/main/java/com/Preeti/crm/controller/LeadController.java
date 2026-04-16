package com.Preeti.crm.controller;

import com.Preeti.crm.model.Lead;
import com.Preeti.crm.service.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (lead.getAssignedSalesRep() == null || lead.getAssignedSalesRep().isEmpty()) {
            lead.setAssignedSalesRep(auth.getName());
        }
        return ResponseEntity.ok(leadService.create(lead));
    }

    @GetMapping
    public ResponseEntity<List<Lead>> getLeads() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return ResponseEntity.status(401).build();

        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        List<Lead> allLeads = leadService.getAll();

        if (allLeads == null) return ResponseEntity.ok(Collections.emptyList());

        if (isAdmin) return ResponseEntity.ok(allLeads);

        String salesRep = auth.getName();
        return ResponseEntity.ok(allLeads.stream()
                .filter(l -> l.getAssignedSalesRep() != null && l.getAssignedSalesRep().equals(salesRep))
                .toList());
    }

    // ✅ NEW: Update Lead
    @PutMapping("/{id}")
    public ResponseEntity<Lead> updateLead(@PathVariable String id, @RequestBody Lead lead) {
        lead.setId(id); // Preserve ID
        return ResponseEntity.ok(leadService.create(lead));
    }

    // ✅ NEW: Delete Lead
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable String id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
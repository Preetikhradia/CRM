package com.Preeti.crm.service;

import com.Preeti.crm.model.Customer;
import com.Preeti.crm.model.Lead;
import com.Preeti.crm.repository.CustomerRepository;
import com.Preeti.crm.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;

    public LeadService(LeadRepository leadRepository,
                       CustomerRepository customerRepository) {
        this.leadRepository = leadRepository;
        this.customerRepository = customerRepository;
    }

    public Lead create(Lead lead) {
        lead.setStatus("NEW");
        return leadRepository.save(lead);
    }

    public List<Lead> getAll() {
        return leadRepository.findAll();
    }

    public Lead updateStatus(String id, String status) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        lead.setStatus(status);

        // 🔁 Convert Lead → Customer
        if ("CONVERTED".equals(status)) {
            Customer customer = new Customer();
            customer.setName(lead.getName());
            customer.setEmail(lead.getEmail());
            customer.setPhone(lead.getPhone());
            customer.setAssignedSalesRepId(lead.getAssignedSalesRep());

            customerRepository.save(customer);
        }

        return leadRepository.save(lead);
    }

    public void delete(String id) {
        leadRepository.deleteById(id);
    }
}

package com.Preeti.crm.controller;

import com.Preeti.crm.model.Customer;
import com.Preeti.crm.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.create(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    // ✅ NEW: Update Customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customer.setId(id);
        return ResponseEntity.ok(customerService.create(customer));
    }

    // ✅ NEW: Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
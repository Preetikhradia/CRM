package com.Preeti.crm.service;

import com.Preeti.crm.model.Customer;
import com.Preeti.crm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public List<Customer> getBySalesRep(String salesRepId) {
        return customerRepository.findByAssignedSalesRepId(salesRepId);
    }

    public Customer getById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer update(String id, Customer updatedCustomer) {
        Customer customer = getById(id);

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setCompany(updatedCustomer.getCompany());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setNotes(updatedCustomer.getNotes());
        customer.setAssignedSalesRepId(updatedCustomer.getAssignedSalesRepId());

        return customerRepository.save(customer);
    }

    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}

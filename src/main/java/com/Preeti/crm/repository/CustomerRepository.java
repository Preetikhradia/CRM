package com.Preeti.crm.repository;

import com.Preeti.crm.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByAssignedSalesRepId(String salesRepId);
}

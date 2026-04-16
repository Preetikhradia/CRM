package com.Preeti.crm.repository;

import com.Preeti.crm.model.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LeadRepository extends MongoRepository<Lead, String> {

    List<Lead> findByAssignedSalesRep(String salesRep);

    List<Lead> findByStatus(String status);
}

package com.Preeti.crm.repository;

import com.Preeti.crm.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SaleRepository extends MongoRepository<Sale, String> {
    List<Sale> findByAssignedRep(String assignedRep);
}
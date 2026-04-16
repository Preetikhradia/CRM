package com.Preeti.crm.repository;

import com.Preeti.crm.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
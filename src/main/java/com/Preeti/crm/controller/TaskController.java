package com.Preeti.crm.controller;

import com.Preeti.crm.model.Task;
import com.Preeti.crm.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // Optional: Delete Task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
    }
}
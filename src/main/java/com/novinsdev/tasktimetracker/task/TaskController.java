package com.novinsdev.tasktimetracker.task;

import com.novinsdev.tasktimetracker.project.Project;
import com.novinsdev.tasktimetracker.project.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;

  public TaskController(TaskRepository taskRepository,
      ProjectRepository projectRepository) {
    this.taskRepository = taskRepository;
    this.projectRepository = projectRepository;
  }

  // Create a new task
  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
    Project project = projectRepository.findById(request.projectId())
        .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + request.projectId()));

    Task task = new Task(request.title(), request.status(), project);
    Task saved = taskRepository.save(task);

    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // Get tasks, optionally filtered by projectId
  @GetMapping
  public List<Task> getTasks(@RequestParam(required = false) Long projectId) {
    if (projectId != null) {
      return taskRepository.findByProject_Id(projectId);
    }
    return taskRepository.findAll();
  }

  // Get a single task by id
  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return taskRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

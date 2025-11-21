package com.novinsdev.tasktimetracker.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectRepository projectRepository;

  public ProjectController(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  // Create a new project
  @PostMapping
  public ResponseEntity<Project> createProject(@RequestBody ProjectRequest request) {
    Project project = new Project(request.name(), request.description());
    Project saved = projectRepository.save(project);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // Get all projects
  @GetMapping
  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  // Get a single project by id
  @GetMapping("/{id}")
  public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
    return projectRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

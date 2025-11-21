package com.novinsdev.tasktimetracker.project;

import com.novinsdev.tasktimetracker.task.TaskRepository;
import com.novinsdev.tasktimetracker.timeentry.TimeEntryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectRepositoryTest {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private TimeEntryRepository timeEntryRepository;

  @Test
  @DisplayName("should save and load a project")
  void saveAndLoadProject() {
    // clean DB in FK-safe order
    timeEntryRepository.deleteAll();
    taskRepository.deleteAll();
    projectRepository.deleteAll();

    Project project = new Project("Test Project", "JUnit repository test");
    Project saved = projectRepository.save(project);

    List<Project> all = projectRepository.findAll();

    assertThat(saved.getId()).isNotNull();
    assertThat(all).extracting(Project::getName).contains("Test Project");
  }
}

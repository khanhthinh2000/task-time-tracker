package com.novinsdev.tasktimetracker.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.novinsdev.tasktimetracker.task.TaskRepository;
import com.novinsdev.tasktimetracker.timeentry.TimeEntryRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TimeEntryRepository timeEntryRepository;

  @Test
  @DisplayName("POST /api/projects should create project and return 201")
  void createProject() throws Exception {
    timeEntryRepository.deleteAll();
    taskRepository.deleteAll();
    projectRepository.deleteAll();

    ProjectRequest request = new ProjectRequest("API Test Project", "From controller test");

    mockMvc.perform(
        post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("API Test Project"));

    List<Project> all = projectRepository.findAll();
    assertThat(all).hasSize(1);
    assertThat(all.get(0).getName()).isEqualTo("API Test Project");
  }

  @Test
  @DisplayName("GET /api/projects should return list of projects")
  void getAllProjects() throws Exception {
    timeEntryRepository.deleteAll();
    taskRepository.deleteAll();
    projectRepository.deleteAll();

    projectRepository.save(new Project("P1", "First project"));
    projectRepository.save(new Project("P2", "Second project"));

    mockMvc.perform(get("/api/projects"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }
}

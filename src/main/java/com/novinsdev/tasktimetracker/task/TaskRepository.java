package com.novinsdev.tasktimetracker.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

  // derived query: task.project.id = :projectId
  List<Task> findByProject_Id(Long projectId);
}

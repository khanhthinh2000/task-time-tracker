package com.novinsdev.tasktimetracker.timeentry;

import com.novinsdev.tasktimetracker.task.Task;
import com.novinsdev.tasktimetracker.task.TaskRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {

  private final TimeEntryRepository timeEntryRepository;
  private final TaskRepository taskRepository;

  public TimeEntryController(TimeEntryRepository timeEntryRepository,
      TaskRepository taskRepository) {
    this.timeEntryRepository = timeEntryRepository;
    this.taskRepository = taskRepository;
  }

  // Create a new time entry for a task
  @PostMapping
  public ResponseEntity<TimeEntry> createTimeEntry(@RequestBody TimeEntryRequest request) {
    Task task = taskRepository.findById(request.taskId())
        .orElseThrow(() -> new IllegalArgumentException("Task not found with id " + request.taskId()));

    LocalDate date = LocalDate.parse(request.workDate()); // expects "YYYY-MM-DD"

    TimeEntry entry = new TimeEntry(task, date, request.hours(), request.note());
    TimeEntry saved = timeEntryRepository.save(entry);

    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // Get time entries, filtered by taskId (required) and optional date range
  @GetMapping
  public List<TimeEntry> getTimeEntries(
      @RequestParam Long taskId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    if (startDate != null && endDate != null) {
      return timeEntryRepository.findByTask_IdAndWorkDateBetween(taskId, startDate, endDate);
    }
    return timeEntryRepository.findByTask_Id(taskId);
  }

  // Get a single time entry by id
  @GetMapping("/{id}")
  public ResponseEntity<TimeEntry> getTimeEntryById(@PathVariable Long id) {
    return timeEntryRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

package com.novinsdev.tasktimetracker.timeentry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novinsdev.tasktimetracker.task.Task;
import jakarta.persistence.*;

import java.time.LocalDate;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Table(name = "time_entries")
public class TimeEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id", nullable = false)
  private Task task;

  @Column(nullable = false)
  private LocalDate workDate;

  @Column(nullable = false)
  private double hours;

  private String note;

  public TimeEntry() {
  }

  public TimeEntry(Task task, LocalDate workDate, double hours, String note) {
    this.task = task;
    this.workDate = workDate;
    this.hours = hours;
    this.note = note;
  }

  public Long getId() {
    return id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public LocalDate getWorkDate() {
    return workDate;
  }

  public void setWorkDate(LocalDate workDate) {
    this.workDate = workDate;
  }

  public double getHours() {
    return hours;
  }

  public void setHours(double hours) {
    this.hours = hours;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}

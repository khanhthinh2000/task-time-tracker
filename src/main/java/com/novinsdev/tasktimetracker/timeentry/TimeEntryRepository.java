package com.novinsdev.tasktimetracker.timeentry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

  List<TimeEntry> findByTask_Id(Long taskId);

  List<TimeEntry> findByTask_IdAndWorkDateBetween(Long taskId, LocalDate start, LocalDate end);
}

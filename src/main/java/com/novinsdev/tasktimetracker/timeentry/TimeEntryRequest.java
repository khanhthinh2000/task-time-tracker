package com.novinsdev.tasktimetracker.timeentry;

public record TimeEntryRequest(
    Long taskId,
    String workDate, // ISO date string: "2025-11-21"
    double hours,
    String note) {
}

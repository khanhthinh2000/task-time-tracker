package com.novinsdev.tasktimetracker.task;

public record TaskRequest(
    String title,
    TaskStatus status,
    Long projectId) {
}
package com.codesolution.projectmanagement.dtos;

import com.codesolution.projectmanagement.models.Task;

import java.util.Date;

public record TaskDTO(
        Integer id,
        String name,
        String description,
        Integer priority,
        Date dueDate,
        String status,
        String projectName
) {
    public TaskDTO(Task task) {
        this(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate(),
                task.getStatus(),
                task.getProject().getName()
        );
    }
}

package com.codesolution.projectmanagement.dtos;

import java.util.Date;

public record TaskDTO(
        String name,
        String description,
        Integer priority,
        Date dueDate,
        String status
) {}

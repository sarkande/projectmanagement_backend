package com.codesolution.projectmanagement.services;

import com.codesolution.projectmanagement.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAllByProjectId(Integer projectId);
    Task findById(Integer projectId, Integer taskId);
    int create(Integer projectId, Task task);
    void update(Integer projectId, Integer taskId, Task task);
    void updatePartial(Integer projectId, Integer taskId, Task oldTask, Task newTask);
    void delete(Integer projectId, Integer taskId);
}

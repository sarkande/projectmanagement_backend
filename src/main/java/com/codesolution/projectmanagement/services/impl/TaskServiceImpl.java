package com.codesolution.projectmanagement.services.impl;

import com.codesolution.projectmanagement.dao.TaskRepository;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.Task;
import com.codesolution.projectmanagement.services.ProjectService;
import com.codesolution.projectmanagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;


    @Override
    public List<Task> findAllByProjectId(Integer projectId) {
        projectService.findById(projectId);

        List<Task> tasks = new ArrayList<>();
        taskRepository.findByProjectId(projectId).forEach(tasks::add);
        return tasks;
    }

    @Override
    public Task findById(Integer projectId, Integer taskId) {
        projectService.findById(projectId);


        Optional<Task> task = taskRepository.findByProjectIdAndId( projectId,taskId);
        if (!task.isPresent())
            throw new EntityDontExistException();

        return task.get();
    }

    @Override
    public int create(Integer projectId, Task task) {
        projectService.findById(projectId);

        return taskRepository.save(task).getId();
    }

    @Override
    public void update(Integer projectId, Integer taskId, Task task) {
        projectService.findById(projectId);

        task.setId(taskId);
        taskRepository.save(task);
    }

    @Override
    public void updatePartial(Integer projectId, Integer taskId, Task oldTask, Task newTask){
        projectService.findById(projectId);


        if (newTask.getName() != null) {
            oldTask.setName(newTask.getName());
        }
        if (newTask.getDescription() != null) {
            oldTask.setDescription(newTask.getDescription());
        }
        if (newTask.getPriority() != null) {
            oldTask.setPriority(newTask.getPriority());
        }
        if (newTask.getDueDate() != null) {
            oldTask.setDueDate(newTask.getDueDate());
        }
        if (newTask.getStatus() != null) {
            oldTask.setStatus(newTask.getStatus());
        }
        taskRepository.save(oldTask);
    }

    @Override
    public void delete(Integer projectId, Integer taskId) {
        projectService.findById(projectId);

        taskRepository.deleteById(taskId);
    }
}

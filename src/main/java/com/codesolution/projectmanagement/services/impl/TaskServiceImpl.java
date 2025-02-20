package com.codesolution.projectmanagement.services.impl;

import com.codesolution.projectmanagement.dao.ProjectRepository;
import com.codesolution.projectmanagement.dao.TaskRepository;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.exceptions.ProjectNotFoundException;
import com.codesolution.projectmanagement.models.Task;
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
    private ProjectRepository projectRepository;


    @Override
    public List<Task> findAllByProjectId(Integer projectId) {
        projectRepository.findById(projectId).get();

        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @Override
    public Task findById(Integer projectId, Integer taskId) {
        projectRepository.findById(projectId).get();


        Optional<Task> task = taskRepository.findById(taskId);
        if (!task.isPresent())
            throw new EntityDontExistException();
        return task.get();
    }

    @Override
    public int create(Integer projectId, Task task) {
        projectRepository.findById(projectId).get();

        return taskRepository.save(task).getId();
    }

    @Override
    public void update(Integer projectId, Integer taskId, Task task) {
        projectRepository.findById(projectId).get();

        task.setId(taskId);
        taskRepository.save(task);
    }

    @Override
    public void updatePartial(Integer projectId, Integer taskId, Task oldTask, Task newTask){
        projectRepository.findById(projectId).get();


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
        projectRepository.findById(projectId).get();

        taskRepository.deleteById(taskId);
    }
}

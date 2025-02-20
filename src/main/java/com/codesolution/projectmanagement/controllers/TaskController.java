package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.models.Task;
import com.codesolution.projectmanagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/{projectId}") // Préfixe pour toutes les routes de ce contrôleur
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Lister toutes les tâches d'un projet
    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasksByProjectId(@PathVariable Integer projectId) {
        return taskService.findAllByProjectId(projectId);
    }

    // Ajouter une tâche à un projet
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addTaskToProject(@PathVariable Integer projectId, @RequestBody Task task) {
        return taskService.create(projectId, task);
    }

    // Obtenir une tâche spécifique d'un projet
    @GetMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@PathVariable Integer projectId,@PathVariable Integer taskId) {
        return taskService.findById(projectId, taskId);
    }

    // Mettre à jour une tâche spécifique d'un projet
    @PutMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@PathVariable Integer projectId,@PathVariable Integer taskId, @RequestBody Task task) {
        taskService.update(projectId, taskId, task);
    }

    // Mise à jour partielle d'une tâche d'un projet
    @PatchMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePartialTask(@PathVariable Integer projectId,@PathVariable Integer taskId, @RequestBody Task newTask) {
        Task oldTask = taskService.findById(projectId, taskId);
        taskService.updatePartial(projectId, taskId, oldTask, newTask);
    }

    // Supprimer une tâche d'un projet
    @DeleteMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer projectId,@PathVariable Integer taskId) {
        taskService.delete(projectId, taskId);
    }
}

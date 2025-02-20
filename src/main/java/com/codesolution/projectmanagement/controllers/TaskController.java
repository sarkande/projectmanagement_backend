package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.dao.ProjectRepository;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.Task;
import com.codesolution.projectmanagement.services.ProjectService;
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

    @Autowired
    private ProjectRepository projectRepository;

    @ModelAttribute("project")
    public Project validateProject(@ModelAttribute("project") Project project) {
        return projectRepository.findById(project.getId())
                .orElseThrow(() -> new EntityDontExistException("Projet non trouvé"));
    }

    // Lister toutes les tâches d'un projet
    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasksByProjectId(@ModelAttribute("project") Project project) {
        return taskService.findAllByProjectId(project.getId());
    }

    // Ajouter une tâche à un projet
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addTaskToProject(@ModelAttribute("project") Project project, @RequestBody Task task) {
        return taskService.create(project.getId(), task);
    }

    // Obtenir une tâche spécifique d'un projet
    @GetMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@ModelAttribute("project") Project project,@PathVariable Integer taskId) {
        return taskService.findById(project.getId(), taskId);
    }

    // Mettre à jour une tâche spécifique d'un projet
    @PutMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@ModelAttribute("project") Project project,@PathVariable Integer taskId, @RequestBody Task task) {
        taskService.update(project.getId(), taskId, task);
    }

    // Mise à jour partielle d'une tâche d'un projet
    @PatchMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePartialTask(@ModelAttribute("project") Project project,@PathVariable Integer taskId, @RequestBody Task newTask) {
        Task oldTask = taskService.findById(project.getId(), taskId);
        taskService.updatePartial(project.getId(), taskId, oldTask, newTask);
    }

    // Supprimer une tâche d'un projet
    @DeleteMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@ModelAttribute("project") Project project,@PathVariable Integer taskId) {
        taskService.delete(project.getId(), taskId);
    }
}

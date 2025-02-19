package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    private ProjectService projectService;

    @GetMapping("/projects")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }

    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable Integer id) {
        return projectService.findById(id);
    }

    @PostMapping("/project")
    public Integer createProject(@RequestBody Project project) {
        return projectService.created(project);
    }

    @PutMapping("/project/{id}")
    public void updateProject(@PathVariable Integer id, @RequestBody Project project) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        projectService.findById(id);

        projectService.update(id, project);
    }

    @PatchMapping("/project/{id}")
    public void updatePartialProject(@PathVariable Integer id, @RequestBody Project newProject) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        Project oldProject = projectService.findById(id);

        projectService.updatePartial(id, oldProject, newProject);
    }

    @DeleteMapping("/project/{id}")
    public void deleteProject(@PathVariable Integer id) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        projectService.findById(id);
        projectService.delete(id);
    }
}

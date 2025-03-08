package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.dtos.UserDTO;
import com.codesolution.projectmanagement.exceptions.BadRequestException;
import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.ProjectUser;
import com.codesolution.projectmanagement.models.User;
import com.codesolution.projectmanagement.services.ProjectService;
import com.codesolution.projectmanagement.services.ProjectUserService;
import com.codesolution.projectmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectUserService projectUserService;

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
    public Integer createProject(@RequestBody Project project, @RequestParam(required = false) Integer userId) {
        //Pour créer un projet, on doit également passer l'id de l'utilisateur qui a créé le projet
        //On creera le projet et on fera une association entre le projet et l'utilisateur
        //En utilisant ProjectUser (voir la classe ProjectUser)

        //Si on a pas de userId, on va bloquer la création du projet  et retourner un code 400
        if (userId == null) {
            throw new BadRequestException("userId is required");
        }
        //On verifie si l'utilisateur existe
        User user = userService.findUserById(userId);

        Integer projectId = projectService.created(project);
        Project projectCreated = projectService.findById(projectId);


        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(projectCreated);
        projectUser.setUser(user);
        projectUser.setRole("Administrateur");

        projectUserService.created(projectUser);


        return projectId;
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

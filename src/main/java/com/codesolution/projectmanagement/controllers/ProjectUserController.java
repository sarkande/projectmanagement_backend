package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.dao.ProjectRepository;
import com.codesolution.projectmanagement.dtos.UserWithRoleDTO;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.ProjectUser;
import com.codesolution.projectmanagement.models.User;
import com.codesolution.projectmanagement.services.ProjectService;
import com.codesolution.projectmanagement.services.ProjectUserService;
import com.codesolution.projectmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project/{projectId}") // Préfixe pour toutes les routes de ce contrôleur
public class ProjectUserController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectUserService projectUserService;

    @ModelAttribute("project")
    public Project validateProject(@PathVariable Integer projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityDontExistException("Projet non trouvé"));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserWithRoleDTO> getUsersWithRolesByProject(@ModelAttribute("project") Project project) {
        return projectUserService.findUsersWithRolesByProjectId(project.getId());
    }

}
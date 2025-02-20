package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.dao.ProjectRepository;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/{projectId}") // Préfixe pour toutes les routes de ce contrôleur
public class ProjectUserController {

    @Autowired
    private ProjectRepository projectRepository;

    @ModelAttribute("project")
    public Project validateProject(@ModelAttribute("project") Project project) {
        return projectRepository.findById(project.getId())
                .orElseThrow(() -> new EntityDontExistException("Projet non trouvé"));
    }



}
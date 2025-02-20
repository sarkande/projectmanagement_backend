package com.codesolution.projectmanagement.services.impl;

import com.codesolution.projectmanagement.dao.ProjectRepository;
import com.codesolution.projectmanagement.dao.ProjectUserRepository;
import com.codesolution.projectmanagement.dao.UserRepository;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.ProjectUser;
import com.codesolution.projectmanagement.models.User;
import com.codesolution.projectmanagement.services.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ProjectUser> findAll() {
        List<ProjectUser> projectUsers = new ArrayList<ProjectUser>();
        projectUserRepository.findAll().forEach(projectUsers::add);
        return projectUsers;
    }

    @Override
    public ProjectUser findById(Integer id) {
        return projectUserRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Relation projet-utilisateur non trouvée"));
    }

    @Override
    public int created(ProjectUser projectUser) {
        // Vérifier que le projet et l'utilisateur existent
        validateProjectAndUserExist(projectUser.getProject().getId(), projectUser.getUser().getId());

        return projectUserRepository.save(projectUser).getId();
    }

    @Override
    public void update(Integer id, ProjectUser projectUser) {
        // Vérifier que la relation existe
        ProjectUser existingProjectUser = projectUserRepository.findById(id).orElseThrow(() -> new EntityDontExistException("Relation projet-utilisateur non trouvée"));

        // Vérifier que le nouveau projet et utilisateur existent
        validateProjectAndUserExist(projectUser.getProject().getId(), projectUser.getUser().getId());

        projectUser.setId(id);
        projectUserRepository.save(projectUser);
    }

    @Override
    public void updatePartial(Integer id, ProjectUser oldProjectUser, ProjectUser newProjectUser) {
        // Vérifier que la relation existe
        ProjectUser existingProjectUser = projectUserRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Relation projet-utilisateur non trouvée"));

        if (newProjectUser.getRole() != null) {
            oldProjectUser.setRole(newProjectUser.getRole());
        }

        projectUserRepository.save(oldProjectUser);
    }

    @Override
    public void delete(Integer id) {
        // Vérifier que la relation existe
        ProjectUser existingProjectUser = projectUserRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Relation projet-utilisateur non trouvée"));

        projectUserRepository.deleteById(id);
    }

    // Méthode utilitaire pour valider l'existence du projet et de l'utilisateur
    private void validateProjectAndUserExist(Integer projectId, Integer userId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityDontExistException("Projet avec ID " + projectId + " non trouvé");
        }
        if (!userRepository.existsById(userId)) {
            throw new EntityDontExistException("Utilisateur avec ID " + userId + " non trouvé");
        }
    }
}

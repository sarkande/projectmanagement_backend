package com.codesolution.projectmanagement.dao;

import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.ProjectUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectUserRepository extends CrudRepository<ProjectUser, Integer> {
    // Trouver par ID du projet (colonne "project" en base)
    List<ProjectUser> findByProject_Id(Integer projectId);

    // Trouver par ID de l'utilisateur (colonne "user" en base)
    List<ProjectUser> findByUser_Id(Integer userId);}

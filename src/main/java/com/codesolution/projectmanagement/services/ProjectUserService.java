package com.codesolution.projectmanagement.services;

import com.codesolution.projectmanagement.models.ProjectUser;
import java.util.List;

public interface ProjectUserService {
    List<ProjectUser> findAll();
    ProjectUser findById(Integer id);
    int created(ProjectUser projectUser);
    void update(Integer id, ProjectUser projectUser);
    void updatePartial(Integer id, ProjectUser oldProjectUser, ProjectUser newProjectUser);
    void delete(Integer id);
}

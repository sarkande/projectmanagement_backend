package com.codesolution.projectmanagement.dao;

import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.ProjectUser;
import org.springframework.data.repository.CrudRepository;

public interface ProjectUserRepository extends CrudRepository<ProjectUser, Integer> {

}

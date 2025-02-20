package com.codesolution.projectmanagement.dao;

import com.codesolution.projectmanagement.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}

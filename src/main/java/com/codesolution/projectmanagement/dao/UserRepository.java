package com.codesolution.projectmanagement.dao;

import com.codesolution.projectmanagement.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}

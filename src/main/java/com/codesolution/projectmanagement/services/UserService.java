package com.codesolution.projectmanagement.services;

import com.codesolution.projectmanagement.models.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();
    public User findById(Integer id);
    public int created(User user);
    public void update(Integer id, User user);
    public void updatePartial(Integer id, User oldUser, User newUser);
    public void delete(Integer id);

    public User login(String email, String password);
}

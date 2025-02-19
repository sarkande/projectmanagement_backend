package com.codesolution.projectmanagement.services.impl;

import com.codesolution.projectmanagement.dao.UserRepository;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.User;
import com.codesolution.projectmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent())
            throw new EntityDontExistException();

        return user.get();
    }

    @Override
    public int created(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public void update(Integer id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public void updatePartial(Integer id, User oldUser, User newUser) {
        //username, email, password
        if(newUser.getUsername() != null){
            oldUser.setUsername(newUser.getUsername());
        }
        if(newUser.getEmail() != null){
            oldUser.setEmail(newUser.getEmail());
        }
        if(newUser.getPassword() != null){
            oldUser.setPassword(newUser.getPassword());
        }

        userRepository.save(oldUser);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}

package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.models.User;
import com.codesolution.projectmanagement.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping("/user")
    public Integer createUser(@RequestBody User user) {
        return userService.created(user);
    }

    @PutMapping("/user/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        userService.findById(id);

        userService.update(id, user);
    }

    @PatchMapping("/user/{id}")
    public void updatePartialUser(@PathVariable Integer id, @RequestBody User newUser) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        User oldUser = userService.findById(id);

        userService.updatePartial(id, oldUser, newUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        userService.findById(id);
        userService.delete(id);
    }
}

package com.codesolution.projectmanagement.controllers;

import com.codesolution.projectmanagement.dtos.UserDTO;
import com.codesolution.projectmanagement.dtos.UserLoginDTO;
import com.codesolution.projectmanagement.exceptions.EntityDontExistException;
import com.codesolution.projectmanagement.models.Project;
import com.codesolution.projectmanagement.models.User;
import com.codesolution.projectmanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/user/login")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDTO login(@Valid @RequestBody UserLoginDTO user) {
        UserDTO user_logged = userService.login(user.email(), user.password());
        if (user_logged == null) {
            throw new EntityDontExistException("User not found");
        }
        return user_logged;
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Integer> createUser(@Valid @RequestBody User user) {
        Integer userId = userService.created(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);    }

    @PutMapping("/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody User user
    ) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        userService.findById(id);

        userService.update(id, user);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> updatePartialUser(
            @PathVariable Integer id,
            @Valid @RequestBody User newUser
    ) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        User oldUser = userService.findUserById(id);

        userService.updatePartial(id, oldUser, newUser);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteUser(@PathVariable Integer id) {
        //On appelle la méthode findById pour vérifier si l'utilisateur existe,
        // s'il n'existe pas on declenchera automatiquement l'exception EntityDontExistException
        userService.findById(id);
        userService.delete(id);
    }

    @GetMapping("/user/{id}/projects")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Project> getUserProjects(@PathVariable Integer id) {
        return userService.findUserProjects(id);
    }
}

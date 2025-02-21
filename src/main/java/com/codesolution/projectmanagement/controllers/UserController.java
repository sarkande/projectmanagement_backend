package com.codesolution.projectmanagement.controllers;

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
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public User getUserById(@PathVariable Integer id) {
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
        User oldUser = userService.findById(id);

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
}

package com.tui.proof.controller;


import com.tui.proof.dto.APIResponse;
import com.tui.proof.dto.UserDTO;
import com.tui.proof.persistence.model.User;
import com.tui.proof.mapper.UserMapper;
import com.tui.proof.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Pedro Silva on 19/05/2024
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/create")
    public ResponseEntity<APIResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity(new APIResponse<>(UserMapper.getUser(newUser)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> getUser(@PathVariable("id") Long id) {

        Optional<User> user = userService.getUser(id);

        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new APIResponse<>(UserMapper.getUser(user.get())));
    }
}

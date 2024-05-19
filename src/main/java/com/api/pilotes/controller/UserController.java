package com.api.pilotes.controller;


import com.api.pilotes.dto.APIResponse;
import com.api.pilotes.dto.OrderDTO;
import com.api.pilotes.dto.UserDTO;
import com.api.pilotes.entity.Order;
import com.api.pilotes.entity.User;
import com.api.pilotes.mapper.OrderMapper;
import com.api.pilotes.mapper.UserMapper;
import com.api.pilotes.service.OrderService;
import com.api.pilotes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

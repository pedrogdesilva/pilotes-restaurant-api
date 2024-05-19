package com.api.pilotes.service;

import com.api.pilotes.dto.UserDTO;
import com.api.pilotes.entity.User;
import com.api.pilotes.mapper.UserMapper;
import com.api.pilotes.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Pedro Silva on 19/05/2024
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        User user = UserMapper.getUser(userDTO);
        return userRepository.save(user);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }
}

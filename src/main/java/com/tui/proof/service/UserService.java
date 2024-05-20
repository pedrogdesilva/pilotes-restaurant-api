package com.tui.proof.service;

import com.tui.proof.dto.UserDTO;
import com.tui.proof.persistence.model.User;
import com.tui.proof.mapper.UserMapper;
import com.tui.proof.persistence.UserRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = UserMapper.getUser(userDTO);
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }
}

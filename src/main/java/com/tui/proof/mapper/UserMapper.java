package com.tui.proof.mapper;

import com.tui.proof.dto.UserDTO;
import com.tui.proof.persistence.model.User;

/**
 * @author Pedro Silva on 19/05/2024
 */
public class UserMapper {

    public static User getUser(UserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

    public static UserDTO getUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        );
    }
}

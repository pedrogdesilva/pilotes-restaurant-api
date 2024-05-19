package com.api.pilotes.mapper;

import com.api.pilotes.dto.UserDTO;
import com.api.pilotes.entity.User;

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

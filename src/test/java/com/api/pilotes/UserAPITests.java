package com.api.pilotes;

import com.api.pilotes.dto.UserDTO;
import com.api.pilotes.entity.User;
import com.api.pilotes.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_Ok() throws Exception {

        // Set up parameters
        UserDTO dto = new UserDTO(null, "Pedro", "Silva", "968564851");

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        // Validate DB state
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(1, users.size());
        User user = users.get(0);
        assertNotNull(user.getId());
        assertEquals(user.getFirstName(), "Pedro");
        assertEquals(user.getLastName(), "Silva");
        assertEquals(user.getPhoneNumber(), "968564851");
    }


    @Test
    void getUser_NotFound() throws Exception {

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void getUser_Ok() throws Exception {

        // Set up parameters
        UserDTO dto = new UserDTO(null, "Pedro", "Silva", "968564851");

        // Create User
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isCreated());

        // Perform Request to get User
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                "{\"data\":{\"id\":1,\"fstName\":\"Pedro\",\"lastName\":\"Silva\",\"phoneNumber\":\"968564851\"},\"hasError\":false,\"errorMessage\":\"\"}");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

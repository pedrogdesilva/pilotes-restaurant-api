package com.tui.proof;

import com.tui.proof.dto.UserDTO;
import com.tui.proof.persistence.model.User;
import com.tui.proof.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAPITests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void createUser_Ok() throws Exception {

        // User
        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");
        user.setOrders(new ArrayList<>(0));

        // Mock Repository
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);

        // Set up parameters
        UserDTO dto = new UserDTO(null, "Pedro", "Silva", "968564851");

        // Perform Request
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals("{\"data\":{\"id\":1,\"fstName\":\"Pedro\",\"lastName\":\"Silva\",\"phoneNumber\":\"968564851\"},\"hasError\":false,\"errorMessage\":\"\"}",
                result.getResponse().getContentAsString());
    }


    @Test
    void getUser_NotFound() throws Exception {

        // Mock Repository
        Mockito.when(userRepository.findById(1l))
                .thenReturn(Optional.empty());

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void getUser_Ok() throws Exception {

        // User
        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");
        user.setOrders(new ArrayList<>(0));

        // Mock Repository
        Mockito.when(userRepository.findById(1l))
                .thenReturn(Optional.of(user));


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

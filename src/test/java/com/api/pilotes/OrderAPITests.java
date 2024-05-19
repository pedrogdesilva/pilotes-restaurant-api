package com.api.pilotes;

import com.api.pilotes.dto.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder_UserNotFound() throws Exception {

        // Set up parameters
        OrderDTO dto = new OrderDTO(null, 1L, "Address for test purposes",  5, LocalDateTime.now(), null);

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void createOrder_InvalidPilotAmount() throws Exception {


        // Set up parameters
        OrderDTO dto = new OrderDTO(null, 1L, "Address for test purposes",  1, LocalDateTime.now(), null);

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

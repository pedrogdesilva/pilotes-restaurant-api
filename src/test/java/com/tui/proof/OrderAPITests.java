package com.tui.proof;

import com.tui.proof.dto.AddressDTO;
import com.tui.proof.dto.OrderDTO;
import com.tui.proof.persistence.model.Address;
import com.tui.proof.persistence.model.Order;
import com.tui.proof.persistence.model.User;
import com.tui.proof.persistence.OrderRepository;
import com.tui.proof.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.service.OrderService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderAPITests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void createOrder_UserNotFound() throws Exception {

        // Set up parameters
        AddressDTO addressDto = new AddressDTO("Test street","3500-007", "Viseu", "Portugal");
        OrderDTO dto = new OrderDTO(null, 1L, addressDto, 5, LocalDateTime.now(), null);

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void createOrder_InvalidPilotAmount() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        // Set up parameters
        AddressDTO addressDto = new AddressDTO("Test street","3500-007", "Viseu", "Portugal");
        OrderDTO dto = new OrderDTO(null, 1L, addressDto, 1, LocalDateTime.now(), null);

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_Ok() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");
        user.setOrders(new ArrayList<>());

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Set up parameters
        AddressDTO addressDto = new AddressDTO("Test street","3500-007", "Viseu", "Portugal");
        OrderDTO dto = new OrderDTO(null, 1L, addressDto, 5, LocalDateTime.now(), null);

        // Perform Request
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isOk());

        // Validate result
        Mockito.verify(orderRepository).save(Mockito.any(Order.class));
    }

    @Test
    void getOrder_NotFound() throws Exception {

        // Mock DB find (no result)
        Mockito.when(orderRepository.findByUsername("Ana"))
                .thenReturn(new ArrayList<>(0));

        // Perform Request
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/order?user=Ana"))
                .andExpect(status().isOk())
                .andReturn();

        // Validate result
        assertEquals("{\"data\":[],\"hasError\":false,\"errorMessage\":\"\"}",
                result.getResponse().getContentAsString());

        Mockito.verify(orderRepository).findByUsername("Ana");
    }

    @Test
    void getOrder_Ok() throws Exception {

        // User
        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");

        //Address
        Address address = new Address();
        address.setPostcode("3500-007");
        address.setCountry("Portugal");
        address.setCity("Viseu");
        address.setStreet("Test street");

        // Order
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setAmountPilotes(5);
        order.setAddress(address);
        order.setTotalAmount(OrderService.PILOTE_UNIT_PRICE * 5);

        user.setOrders(List.of(order));

        // Mock DB find (one result)
        Mockito.when(orderRepository.findByUsername("Pedro"))
                .thenReturn(List.of(order));

        // Perform Request
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/order?user=Pedro"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("{\"data\":[{\"id\":1,\"userId\":1,\"deliveryAddress\":{\"street\":\"Test street\",\"postcode\":\"3500-007\",\"city\":\"Viseu\",\"country\":\"Portugal\"},\"amountPilotes\":5,\"creationDate\":null,\"total\":6.65}],\"hasError\":false,\"errorMessage\":\"\"}",
                result.getResponse().getContentAsString());
    }


    @Test
    void updateOrder_NotFound() throws Exception {

        // User
        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");

        //Address
        Address address = new Address();
        address.setPostcode("3500-007");
        address.setCountry("Portugal");
        address.setCity("Viseu");
        address.setStreet("Test street");

        // Order
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setAmountPilotes(5);
        order.setAddress(address);
        order.setTotalAmount(OrderService.PILOTE_UNIT_PRICE * 5);

        user.setOrders(List.of(order));

        // Mock DB find
        Mockito.when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // Perform Request
        AddressDTO addressDto = new AddressDTO("Test street","3500-007", "Viseu", "Portugal");
        OrderDTO dto = new OrderDTO(null, 1L, addressDto, 5, LocalDateTime.now(), null);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/order/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateOrder_Ok() throws Exception {

        // User
        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");

        //Address
        Address address = new Address();
        address.setPostcode("3500-007");
        address.setCountry("Portugal");
        address.setCity("Viseu");
        address.setStreet("Test street");

        // Order
        LocalDateTime updateTime = LocalDateTime.now();
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setAmountPilotes(5);
        order.setAddress(address);
        order.setTotalAmount(OrderService.PILOTE_UNIT_PRICE * 5);
        order.setCreationDate(updateTime);

        user.setOrders(List.of(order));

        // Mock DB find
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Mockito.when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Perform Request
        AddressDTO addressDto = new AddressDTO("Test street","3500-007", "Viseu", "Portugal");
        OrderDTO dto = new OrderDTO(null, 1L, addressDto, 10, LocalDateTime.now(), null);
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/order/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("{\"data\":{\"id\":1,\"userId\":1,\"deliveryAddress\":{\"street\":\"Test street\",\"postcode\":\"3500-007\",\"city\":\"Viseu\",\"country\":\"Portugal\"},\"amountPilotes\":10,\"creationDate\":\"" + updateTime.toString().substring(0, updateTime.toString().length()-2) + "\",\"total\":13.3},\"hasError\":false,\"errorMessage\":\"\"}",
                result.getResponse().getContentAsString());
    }

    @Test
    void updateOrder_Error_TimeExceeded() throws Exception {

        // User
        User user = new User();
        user.setId(1L);
        user.setFirstName("Pedro");
        user.setLastName("Silva");
        user.setPhoneNumber("968564851");

        //Address
        Address address = new Address();
        address.setPostcode("3500-007");
        address.setCountry("Portugal");
        address.setCity("Viseu");
        address.setStreet("Test street");

        // Order
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setAmountPilotes(5);
        order.setAddress(address);
        order.setTotalAmount(OrderService.PILOTE_UNIT_PRICE * 5);
        order.setCreationDate(LocalDateTime.now().minusMinutes(6));

        user.setOrders(List.of(order));

        // Mock DB find
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Mockito.when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Perform Request
        AddressDTO addressDto = new AddressDTO("Test street","3500-007", "Viseu", "Portugal");
        OrderDTO dto = new OrderDTO(null, 1L, addressDto, 10, LocalDateTime.now(), null);
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/order/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("{\"data\":null,\"hasError\":true,\"errorMessage\":\"Order is already being processed. Update is not possible :(\"}",
                result.getResponse().getContentAsString());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

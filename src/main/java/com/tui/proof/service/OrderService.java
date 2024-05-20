package com.tui.proof.service;

import com.tui.proof.dto.OrderDTO;
import com.tui.proof.mapper.AddressMapper;
import com.tui.proof.persistence.model.Order;
import com.tui.proof.persistence.model.User;
import com.tui.proof.mapper.OrderMapper;
import com.tui.proof.persistence.OrderRepository;
import com.tui.proof.persistence.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * @author Pedro Silva on 19/05/2024
 */
@Service
public class OrderService {

    public static final Float PILOTE_UNIT_PRICE = 1.33f;
    private static final long UPDATE_WINDOW_MIN = 5;
    private OrderRepository orderRepository;

    private UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.getOrder(orderDTO);

        // Ideally we should have a table to store all the price of all products (scalable in the future)
        order.setTotalAmount(PILOTE_UNIT_PRICE * order.getAmountPilotes());

        // User id validated at controller level
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        order.setUser(user.get());

        return orderRepository.save(order);
    }

    @Transactional
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Optional<Order> updateOrder(Order order, OrderDTO dto) {

        long elapsedTime = ChronoUnit.MINUTES.between(order.getCreationDate(), LocalDateTime.now());

        // Validate if update is possible
        if (elapsedTime > UPDATE_WINDOW_MIN) {
            return Optional.empty();
        }

        // Update Order
        order.setAddress(AddressMapper.getAddress(dto.getDeliveryAddress()));
        order.setAmountPilotes(dto.getAmountPilotes());
        order.setTotalAmount(dto.getAmountPilotes() * PILOTE_UNIT_PRICE);

        return Optional.of(orderRepository.save(order));
    }

    @Transactional
    public List<Order> getUserOrders(String searchName) {
       return orderRepository.findByUsername(searchName);
    }
}

package com.tui.proof.controller;


import com.tui.proof.dto.APIResponse;
import com.tui.proof.dto.OrderDTO;
import com.tui.proof.persistence.model.Order;
import com.tui.proof.mapper.OrderMapper;
import com.tui.proof.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author Pedro Silva on 19/05/2024
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    private OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/create")
    public ResponseEntity<APIResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO order) {
        Order newOrder = orderService.createOrder(order);
        return ResponseEntity.ok().body(new APIResponse<>(OrderMapper.getOrder(newOrder)));
    }

    @GetMapping("")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getUserOrders(@RequestParam("user") String searchName) {

        List<Order> searchResult = orderService.getUserOrders(searchName);

        return ResponseEntity.ok().body(new APIResponse<>(OrderMapper.getOrder(searchResult)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> updateOrder(@PathVariable("id") Long id, @Valid @RequestBody OrderDTO order) {

        Optional<Order> orderToUpdate = orderService.getOrder(id);

        if(orderToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Order> updatedOrder = orderService.updateOrder(orderToUpdate.get(), order);

        if(updatedOrder.isEmpty()) {
            return ResponseEntity.ok().body(new APIResponse<>("Order is already being processed. Update is not possible :("));
        }

        return ResponseEntity.ok().body(new APIResponse<>(OrderMapper.getOrder(updatedOrder.get())));
    }
}

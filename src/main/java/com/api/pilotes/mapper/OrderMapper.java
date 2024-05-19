package com.api.pilotes.mapper;

import com.api.pilotes.dto.OrderDTO;
import com.api.pilotes.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pedro Silva on 19/05/2024
 */
public class OrderMapper {

    public static Order getOrder(OrderDTO dto) {
        Order order = new Order();
        order.setAmountPilotes(dto.getAmountPilotes());
        order.setCreationDate(LocalDateTime.now());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        return order;
    }

    public static OrderDTO getOrder(Order newOrder) {
        return new OrderDTO(
                newOrder.getUser().getId(),
                newOrder.getId(),
                newOrder.getDeliveryAddress(),
                newOrder.getAmountPilotes(),
                newOrder.getCreationDate(),
                newOrder.getTotalAmount()
        );
    }

    public static List<OrderDTO> getOrder(List<Order> searchResult) {
        return searchResult.stream().map(OrderMapper::getOrder).collect(Collectors.toList());
    }
}

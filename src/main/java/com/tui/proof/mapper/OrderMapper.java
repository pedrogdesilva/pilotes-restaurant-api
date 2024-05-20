package com.tui.proof.mapper;

import com.tui.proof.dto.OrderDTO;
import com.tui.proof.persistence.model.Order;

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
        order.setAddress(AddressMapper.getAddress(dto.getDeliveryAddress()));
        return order;
    }

    public static OrderDTO getOrder(Order newOrder) {
        return new OrderDTO(
                newOrder.getUser().getId(),
                newOrder.getId(),
                AddressMapper.getAddress(newOrder.getAddress()),
                newOrder.getAmountPilotes(),
                newOrder.getCreationDate(),
                newOrder.getTotalAmount()
        );
    }

    public static List<OrderDTO> getOrder(List<Order> searchResult) {
        return searchResult.stream().map(OrderMapper::getOrder).collect(Collectors.toList());
    }
}

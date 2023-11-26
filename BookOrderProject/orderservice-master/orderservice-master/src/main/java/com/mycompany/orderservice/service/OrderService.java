package com.mycompany.orderservice.service;

import com.mycompany.orderservice.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    public OrderDTO placeOrder(OrderDTO orderDTO);
    public List<OrderDTO> getAllOrders(Long userId);
}

package com.mycompany.orderservice.controller;


import com.mycompany.orderservice.dto.OrderDTO;
import com.mycompany.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        orderDTO = orderService.placeOrder(orderDTO);
        ResponseEntity<OrderDTO> responseEntity = new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/orders/users/{userId}")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable Long userId){
        List<OrderDTO> orderDTOList = orderService.getAllOrders(userId);
        ResponseEntity<List<OrderDTO>> responseEntity = new ResponseEntity<>(orderDTOList, HttpStatus.OK);
        return responseEntity;
    }
}

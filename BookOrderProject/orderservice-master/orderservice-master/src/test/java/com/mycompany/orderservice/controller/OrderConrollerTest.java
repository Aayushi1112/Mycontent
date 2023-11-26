package com.mycompany.orderservice.controller;

import com.mycompany.orderservice.dto.OrderDTO;
import com.mycompany.orderservice.service.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderConrollerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderServiceImpl orderService;

    @Test
    void placeOrderTest()
    {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setId(1L);
        Mockito.when(orderService.placeOrder(orderDTO)).thenReturn(orderDTO);
        ResponseEntity<OrderDTO> responseEntity =orderController.placeOrder(orderDTO);
        Assertions.assertNotNull(responseEntity.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue(),"expecting status as created");


    }

    @Test
    @Disabled
    void getAllOrdersTest()
    {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        OrderDTO orderDTO=new OrderDTO();
        orderDTOList.add(orderDTO);
        Long userId=1L;
        Mockito.when(orderService.getAllOrders(userId)).thenReturn(orderDTOList);
        ResponseEntity<List<OrderDTO>> responseEntity=orderController.getAllOrders(userId);
        Assertions.assertEquals(1,responseEntity.getBody().size());
        Assertions.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

    }

}

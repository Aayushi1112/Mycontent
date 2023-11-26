package com.mycompany.orderservice.service;

import com.mycompany.orderservice.client.feign.BookFeignClient;
import com.mycompany.orderservice.dto.BookDTO;
import com.mycompany.orderservice.dto.ErrorDTO;
import com.mycompany.orderservice.dto.OrderDTO;
import com.mycompany.orderservice.entity.OrderEntity;
import com.mycompany.orderservice.exception.BusinessException;
import com.mycompany.orderservice.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    //    @Autowired
//    private BookFeignClient bookFeignClient;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${book.service.url}")
    private String bookServiceUrl;

    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(null, headers);
        List<ErrorDTO> errorDTOS = null;
        ParameterizedTypeReference<BookDTO> typeRef = new ParameterizedTypeReference<BookDTO>() {};
        OrderEntity orderEntity = new OrderEntity();
        StringBuilder sb = new StringBuilder();
        for (BookDTO bdto : orderDTO.getBooks()) {
            ResponseEntity<BookDTO> respEntity = this.restTemplate.exchange(this.bookServiceUrl + "/books/{bookId}", HttpMethod.GET, httpEntity, typeRef, bdto.getBookId());
            if (respEntity.getStatusCodeValue() == 200) {
                BookDTO bookDTO = respEntity.getBody();
                if (bookDTO.getAvailableQty() < 1) {
                    ErrorDTO errorDTO = new ErrorDTO();
                    errorDTOS = new ArrayList<>();
                    errorDTO.setCode("QTY_NOT_SUFFICIENT");
                    errorDTO.setMsg("book with id" + bdto.getBookId() + "does not have sufficient quantity");
                    errorDTOS.add(errorDTO);

                } else {
                    sb.append(bdto.getBookId().toString());
                    sb.append(",");
                }
            }
            if (errorDTOS != null && errorDTOS.size() == orderDTO.getBooks().size()) {
                throw new BusinessException(errorDTOS);
            } else {
                //   sb.deleteCharAt(sb.toString().length() - 1);
                orderEntity.setUserId(orderDTO.getUserId());
                ParameterizedTypeReference<String> typeRef11 = new ParameterizedTypeReference<String>() {
                };
                BookDTO book = new BookDTO();
                book.setBookId(null);
                HttpEntity httpEntity11 = new HttpEntity(book, headers);
               // ResponseEntity<String> respEntity1 = this.restTemplate.exchange(this.bookServiceUrl + "/bookqty/{bookId}", HttpMethod.PATCH, httpEntity11, typeRef11, bdto.getBookId());
              //  System.out.println(respEntity1);
                orderEntity.setBookIds(sb.toString());
                orderEntity = orderRepository.save(orderEntity);
                BeanUtils.copyProperties(orderEntity, orderDTO);
            }
        }
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrders(Long userId) {

        List<OrderEntity> orderEntities = orderRepository.findAllByUserId(userId);

        ResponseEntity<BookDTO> re = null;

        List<OrderDTO> orderDtoList = new ArrayList<>();
        BookDTO bookDTO = null;
        OrderDTO orderDTO = null;

        for (OrderEntity oe : orderEntities) {

            String[] bookIds = oe.getBookIds().split(",");
            orderDTO = new OrderDTO();
            List<BookDTO> dtoList=new ArrayList<>();
            for (String bookId : bookIds) {
                // re = bookFeignClient.getBook(Long.parseLong(bookId));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity httpEntity = new HttpEntity(null, headers);
                ParameterizedTypeReference<BookDTO> typeRef = new ParameterizedTypeReference<BookDTO>() {
                };
                ResponseEntity<BookDTO> responseEntity = this.restTemplate.exchange(this.bookServiceUrl + "/books/{bookId}", HttpMethod.GET, httpEntity, typeRef, bookId);
                bookDTO = responseEntity.getBody();
                dtoList.add(bookDTO);
            }
            orderDTO.setId(oe.getId());
            orderDTO.setBooks(dtoList);
            orderDTO.setUserId(userId);
            orderDtoList.add(orderDTO);
        }

        return orderDtoList;
    }
}

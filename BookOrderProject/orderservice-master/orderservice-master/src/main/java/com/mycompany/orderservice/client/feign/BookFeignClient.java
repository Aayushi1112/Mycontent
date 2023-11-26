package com.mycompany.orderservice.client.feign;

import com.mycompany.orderservice.client.config.BookFeignDecoder;
import com.mycompany.orderservice.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookFeignClient", url = "${book.service.url:}", configuration = BookFeignDecoder.class)
public interface BookFeignClient {

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long bookId);
}

package com.mycompany.orderservice.client.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new BookFeignDecoder();
    }
}

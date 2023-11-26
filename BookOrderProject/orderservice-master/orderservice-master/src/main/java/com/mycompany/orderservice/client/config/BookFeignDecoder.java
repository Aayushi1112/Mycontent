package com.mycompany.orderservice.client.config;

import feign.Response;
import feign.codec.ErrorDecoder;

public class BookFeignDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        Exception ex = null;
        if(response.body() != null){
            ex = new Exception("Error occurred while making Feign API call");
        }
        return ex;
    }
}

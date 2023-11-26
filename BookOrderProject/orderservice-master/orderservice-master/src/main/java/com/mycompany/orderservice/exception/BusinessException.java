package com.mycompany.orderservice.exception;

import com.mycompany.orderservice.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException{
    private List<ErrorDTO> errors;
}

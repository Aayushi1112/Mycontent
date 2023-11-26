package com.mycompany.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {
    private Long bookId;
    private String name;
    private String description;
    private Double pricePerQty;
    private Double availableQty;
    private String authorName;
    private String authorEmail;
}

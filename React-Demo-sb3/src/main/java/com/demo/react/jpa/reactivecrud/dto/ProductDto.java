package com.demo.react.jpa.reactivecrud.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private int quantity;
    private double price;
}

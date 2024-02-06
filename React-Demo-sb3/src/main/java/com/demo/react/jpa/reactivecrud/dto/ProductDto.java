package com.demo.react.jpa.reactivecrud.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ProductDto {
    private UUID id;
    private String name;
    private int quantity;
    private double price;
}

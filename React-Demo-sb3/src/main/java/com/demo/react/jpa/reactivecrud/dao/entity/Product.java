package com.demo.react.jpa.reactivecrud.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity (name = "Product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String quantity;
    private double price;
}

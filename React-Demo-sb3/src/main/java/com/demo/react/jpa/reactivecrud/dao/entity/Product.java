package com.demo.react.jpa.reactivecrud.dao.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
//@Entity (name = "Product")
@Table(name = "Product")
public class Product {
//    @Id
//    @GeneratedValue
//    private Long id;

    @Id
    private UUID id = UUID.randomUUID();;

    private String name;
    private String quantity;
    private double price;
}

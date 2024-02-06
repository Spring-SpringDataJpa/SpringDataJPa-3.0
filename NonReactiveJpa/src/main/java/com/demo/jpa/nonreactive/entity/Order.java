package com.demo.jpa.nonreactive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "CustomerOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private Date orderDate;

    //TODO do one poc in the same project using set collection instead of List
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private Set<OrderItem> orderItems = new HashSet<>();

    //mappedBy = "order", order is the field name of Order object inside OrderItem
    @OneToMany(
            mappedBy = "order", // This is the name of the Order type variable inside orderItem entity, since this is a bidirectional relationship
            cascade = CascadeType.ALL,
            // cascade = CascadeType.ALL: If we perform any ooperation on order entity, that operation will also be cascaded to order item entity
            //such as PERSIST, MERGE, REMOVE, DETACH, REFRESH, ALL (This includes all of the above cascade options.)
            orphanRemoval = true // If order is deleted, all orderItem wil also be removed
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

}


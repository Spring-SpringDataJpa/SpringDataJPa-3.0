package com.demo.jpa.nonreactive.repository;

import com.demo.jpa.nonreactive.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

//    @EntityGraph(value = "Order.orderItems", type = EntityGraph.EntityGraphType.L
//    OAD)
//    @EntityGraph(value = "order-entity-graph-with-orderItems", type = EntityGraph.EntityGraphType.LOAD)
//    List<OrderItem> findByOrderId(Long orderId);

    @EntityGraph(value = "orderItem-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<OrderItem> findByOrderId(Long orderId);
//    @EntityGraph(value = "Order.orderItems", type = EntityGraph.EntityGraphType.FETCH)
    Optional<OrderItem> findByIdAndOrderId(Long orderItemId, Long orderId);

//    @EntityGraph(value = "orderItem-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
//    @Query("SELECT oi.productName, oi.price FROM Order_Item oi")
//    List<OrderItem> findAllOrderItems();
}
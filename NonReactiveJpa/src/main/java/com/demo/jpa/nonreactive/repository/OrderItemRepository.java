package com.demo.jpa.nonreactive.repository;

import com.demo.jpa.nonreactive.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @EntityGraph(value = "Order.orderItems", type = EntityGraph.EntityGraphType.FETCH)
    List<OrderItem> findByOrderId(Long orderId);

    @EntityGraph(value = "Order.orderItems", type = EntityGraph.EntityGraphType.FETCH)
    Optional<OrderItem> findByIdAndOrderId(Long orderItemId, Long orderId);
}
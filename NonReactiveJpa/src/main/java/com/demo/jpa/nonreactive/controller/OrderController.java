package com.demo.jpa.nonreactive.controller;

import com.demo.jpa.nonreactive.entity.Order;
import com.demo.jpa.nonreactive.entity.OrderItem;
import com.demo.jpa.nonreactive.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable Long id) {
        return orderService.getOrderItemsByOrderId(id);
    }

    @PostMapping("/{orderId}/items")
    public OrderItem createOrderItem(@PathVariable Long orderId, @RequestBody OrderItem orderItem) {
        return orderService.createOrderItem(orderId, orderItem);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItem getOrderItemById(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public OrderItem updateOrderItem(@PathVariable Long orderId, @PathVariable Long itemId, @RequestBody OrderItem orderItem) {
        return orderService.updateOrderItem(orderId, itemId, orderItem);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public void deleteOrderItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        orderService.deleteOrderItem(orderId, itemId);
    }
}

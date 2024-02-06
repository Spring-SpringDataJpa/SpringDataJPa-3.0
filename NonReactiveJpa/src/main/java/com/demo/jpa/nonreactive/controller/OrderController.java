package com.demo.jpa.nonreactive.controller;

import com.demo.jpa.nonreactive.entity.Order;
import com.demo.jpa.nonreactive.entity.OrderItem;
import com.demo.jpa.nonreactive.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
//@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable("id") Long id) {
        return orderService.getOrderItemsByOrderId(id);
    }

    @PostMapping("/{orderId}/items")
    public OrderItem createOrderItem(@PathVariable("orderId") Long orderId, @RequestBody OrderItem orderItem) {
        return orderService.createOrderItem(orderId, orderItem);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItem getOrderItemById(@PathVariable("orderId") Long orderId, @PathVariable("itemId") Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public OrderItem updateOrderItem(@PathVariable("orderId") Long orderId, @PathVariable("itemId") Long itemId, @RequestBody OrderItem orderItem) {
        return orderService.updateOrderItem(orderId, itemId, orderItem);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public void deleteOrderItem(@PathVariable("orderId") Long orderId, @PathVariable("itemId") Long itemId) {
        orderService.deleteOrderItem(orderId, itemId);
    }
}

package com.demo.jpa.nonreactive.service;

import com.demo.jpa.nonreactive.entity.Order;
import com.demo.jpa.nonreactive.entity.OrderItem;
import com.demo.jpa.nonreactive.exception.ResourceNotFoundException;
import com.demo.jpa.nonreactive.repository.OrderItemRepository;
import com.demo.jpa.nonreactive.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order createOrder(Order order) {
//        List<OrderItem> orderItemsTemp = new ArrayList<>();
//        List<OrderItem> orderItems = order.getOrderItems();
//        for (OrderItem item : orderItems) {
////            item.setOrder(order);
//            orderItemsTemp.add(item);
//        }
//        order.setOrderItems(orderItemsTemp);

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setOrderNumber(order.getOrderNumber());
        existingOrder.setOrderDate(order.getOrderDate());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem createOrderItem(Long orderId, OrderItem orderItem) {
        Order order = getOrderById(orderId);
        orderItem.setOrder(order);
        order.getOrderItems().add(orderItem);
        return orderItemRepository.save(orderItem);
    }

    public OrderItem getOrderItemById(Long orderId, Long orderItemId) {

        return orderItemRepository.findByIdAndOrderId(orderItemId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + orderItemId + " in order with id: " + orderId));
    }

    public OrderItem updateOrderItem(Long orderId, Long orderItemId, OrderItem orderItem) {
        OrderItem existingOrderItem = getOrderItemById(orderId, orderItemId);
        existingOrderItem.setProductName(orderItem.getProductName());
        existingOrderItem.setPrice(orderItem.getPrice());
        existingOrderItem.setQuantity(orderItem.getQuantity());
        return orderItemRepository.save(existingOrderItem);
    }

    public void deleteOrderItem(Long orderId, Long orderItemId) {
        OrderItem orderItem = getOrderItemById(orderId, orderItemId);
        orderRepository.findById(orderId).ifPresent(order -> order.getOrderItems().remove(orderItem));
        orderItemRepository.deleteById(orderItemId);
    }


}

package com.demo.jpa.nonreactive.repository;

import com.demo.jpa.nonreactive.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * findAllWithOrderItems() method defines a custom query method that fetches all Order entities along with their associated OrderItem entities eagerly (at the time of application startup).
     * The @EntityGraph annotation is used to specify the attribute paths to be eagerly fetched, in this case, it's just orderItems.
     */
    @EntityGraph(attributePaths = {"orderItems"})
//    List<Order> findAllWithOrderItems();
    List<Order> findAll();
    /**
     * findByIdWithOrderItems(Long id) method defines another custom query method that fetches a single Order entity by its ID along with its associated OrderItem entities. Similarly, the @EntityGraph annotation ensures that the orderItems collection is eagerly loaded.
     *
     */
    @EntityGraph(attributePaths = {"orderItems"})
    Order findByIdWithOrderItems(Long id);

    /**
     * case: only quantitiy inside orderItem entity will be fetched eagerly, along with all the elements of order entity:
     *
     * findAllWithOrderItemsQuantity() defines a custom query method to fetch all Order entities along with their associated OrderItem entities.
     * The @EntityGraph annotation is used to specify the attribute paths to be eagerly fetched. Here, we specify "orderItems.quantity" to fetch the quantity attribute of the OrderItem entities eagerly while the rest of the attributes are fetched lazily by default.
     *
     */
//    @EntityGraph(attributePaths = {"orderItems.quantity"})
//    List<Order> findAllWithOrderItemsQuantity();

    /**
     * Case: Only fetch attributes orderNumber from Order entity and quantity from OrderItem entity eagerly.
     * Rest other attributes insde Order and OrderItem entities shoudl be fetched lazily.
     * @EntityGraph is used with attributePaths to specify the attributes to be eagerly fetched. We specify "orderNumber" to fetch the orderNumber attribute of the Order entity eagerly, and "orderItems.quantity" to fetch the quantity attribute of the OrderItem entities eagerly.
     *
     * The rest of the attributes inside both Order and OrderItem entities will be fetched lazily by default.
     *
     */
    @EntityGraph(attributePaths = {"orderNumber", "orderItems.quantity"})
    List<Order> findAllWithOrderItemsQuantity();

}
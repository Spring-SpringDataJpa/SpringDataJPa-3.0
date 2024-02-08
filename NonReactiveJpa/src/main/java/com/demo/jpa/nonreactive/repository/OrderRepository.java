package com.demo.jpa.nonreactive.repository;

import com.demo.jpa.nonreactive.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * type = EntityGraph.EntityGraphType.FETCH - It is equivalent to Eager fetching
 *  type = EntityGraph.EntityGraphType.LOAD - It is equivalent to Lazy fetching
 */

/**
 * eager loading ensures that associated entities or attributes are loaded immediately along with the main entity,
 * whenever the main entity is fetched. When exactly this happens (whether during application startup or later) depends
 * on when you query for the entities in your application code.
 * However, if you have repository methods with eager loading but they are not invoked during application startup,
 * the associated fields would be loaded when those repository methods are explicitly called later during the application's lifecycle.
 *
 *
    Can I lazy load any elements of main entity?
     Yes, you can lazily load elements of the main entity in JPA.

     In JPA, you can configure lazy loading for associations or attributes of an
     entity using the fetch attribute in the mapping annotations (@OneToMany, @ManyToOne, etc.) or
     through fetch plans such as @EntityGraph.

     For example, let's say you have an entity Order with a one-to-many association to OrderItem.
     You can configure lazy loading for the OrderItem collection in the Order entity like this:

     @Entity
     public class Order {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
     private List<OrderItem> orderItems;

     // Other fields, constructors, getters and setters
     }

     In this example, the orderItems collection will be lazily loaded. When you fetch an Order entity, the orderItems collection will not be loaded immediately, but will be loaded from the database only when you access it.

     bytecode enhancement tools : Configure lazy loading for individual attributes within an entity
     you can configure lazy loading for individual attributes within an entity using bytecode enhancement tools like Hibernate's lazy loading proxy mechanism. This allows specific fields to be loaded lazily, even within the context of a single entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * findAllWithOrderItems() method defines a custom query method that fetches all Order entities along with their associated OrderItem entities eagerly (at the time of application startup).
     * The @EntityGraph annotation is used to specify the attribute paths to be eagerly fetched, in this case, it's just orderItems.
     */
//    @EntityGraph(attributePaths = {"orderItems"})
//    List<Order> findAllWithOrderItems();
   // List<Order> findAll();

    /**
     * Case: Only fetch attributes orderNumber from Order entity and quantity from OrderItem entity eagerly.
     * Rest other attributes insde Order and OrderItem entities shoudl be fetched lazily.
     * @EntityGraph is used with attributePaths to specify the attributes to be eagerly fetched. We specify "orderNumber" to fetch the orderNumber attribute of the Order entity eagerly, and "orderItems.quantity" to fetch the quantity attribute of the OrderItem entities eagerly.
     *
     * The rest of the attributes inside both Order and OrderItem entities will be fetched lazily by default.
     *
     */
    @EntityGraph(attributePaths = {"orderNumber", "orderItems.quantity"}, type = EntityGraph.EntityGraphType.LOAD)
//    List<Order> findAllWithOrderItemsQuantity();
    List<Order> findAll();

    @EntityGraph(attributePaths = {"orderNumber", "orderItems.quantity"}, type = EntityGraph.EntityGraphType.FETCH)
    Order findByOrderNumber(String orderNumber);

    // please note that the @EntityGraph annotation is typically used to define a graph of attributes to be eagerly fetched when loading entities via JPA repository methods, rather than in native queries like the one you're using with @Query.
//    @EntityGraph(attributePaths = {"orderNumber", "orderItems.quantity"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT o.orderNumber, oi.quantity FROM CustomerOrder o JOIN o.orderItems oi")
    List<Object[]> findOrderNumberAndQuantity();

    /**
     * findByIdWithOrderItems(Long id) method defines another custom query method that fetches a single Order entity by its ID along with its associated OrderItem entities. Similarly, the @EntityGraph annotation ensures that the orderItems collection is eagerly loaded.
     */
    @EntityGraph(attributePaths = {"orderItems"})
//    Order findByIdWithOrderItems(Long id);
    Optional<Order> findById(Long id);
    /**
     * case: only quantitiy inside orderItem entity will be fetched eagerly, along with all the elements of order entity:
     *
     * findAllWithOrderItemsQuantity() defines a custom query method to fetch all Order entities along with their associated OrderItem entities.
     * The @EntityGraph annotation is used to specify the attribute paths to be eagerly fetched. Here, we specify "orderItems.quantity" to fetch the quantity attribute of the OrderItem entities eagerly while the rest of the attributes are fetched lazily by default.
     *
     */
//    @EntityGraph(attributePaths = {"orderItems.quantity"})
//    List<Order> findAllWithOrderItemsQuantity();


}
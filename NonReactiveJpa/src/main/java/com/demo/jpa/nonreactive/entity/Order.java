package com.demo.jpa.nonreactive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *  Default fetch types in various relationship types:
 *  One-to-One - Eager
 *  One-to-Many  - Lazy
 *  Many-to-One - Eager
 *  Many-to-Many - Lazy
 *
 *  @Basic - Eager - It is used to put the annotation for fetch type on the individual elements of the entity.
 *  @ElementCollection - LAZY - It works same as various relationshiops annotations, except that we cannot set relationships
 *  and we cannot do joins on it manually, that means we doon't have control over joins ot unlike varous relationship
 *  types mentioned above. We cannot do joins because it is taking care by Jpa and then hibernate behind the scene.
 *  We can use @ElementCollection for any collection of primitive types such as String or Integer and for putting this annotatio
 *  over collection types of any custom obejct types. That custom object has to have @Enbadable annotation marked on top of that
 *  custom object class.
 */

/**
 * With the help of entity graph we can gain access to FetchType property of each and every element of the entity
 * and it's sub entity, that means related entities.
 * @NamedEntityGraph represents the current entity (for which you are defining repository interface).
 * @NamedSubgraph represents the related entity from the current entity.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "CustomerOrder")
@NamedEntityGraph(
        name = "order-entity-graph-with-orderItems",
        attributeNodes = {
                @NamedAttributeNode("orderNumber"),
                @NamedAttributeNode("orderDate"),
                @NamedAttributeNode(value = "orderItems", subgraph = "orderItemsSubgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "orderItemsSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("productName"),
                                @NamedAttributeNode("price"),
                                @NamedAttributeNode("quantity"),
                                @NamedAttributeNode("order")
                        }
                )
        }
)
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
            fetch = FetchType.EAGER,
            // cascade = CascadeType.ALL: If we perform any ooperation on order entity, that operation will also be cascaded to order item entity
            //such as PERSIST, MERGE, REMOVE, DETACH, REFRESH, ALL (This includes all of the above cascade options.)
            cascade = CascadeType.ALL,
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


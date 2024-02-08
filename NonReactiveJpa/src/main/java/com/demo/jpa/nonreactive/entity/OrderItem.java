package com.demo.jpa.nonreactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "Order_Item")
@NamedEntityGraph(
        name = "orderItem-entity-graph",
        attributeNodes = {
//                @NamedAttributeNode("id"), // no necessary as in many cases, the IDs are automatically fetched along with the entities when they are loaded from the database, so explicitly including them in the entity graph might not be necessary.
                @NamedAttributeNode("productName"),
                @NamedAttributeNode("price")
//                , @NamedAttributeNode("quantity")
//                , @NamedAttributeNode(value = "order", subgraph = "orderSubgraph")
        }
//        ,
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "orderSubgraph",
//                        attributeNodes = {
////                                @NamedAttributeNode("id"), // no necessary as in many cases, the IDs are automatically fetched along with the entities when they are loaded from the database, so explicitly including them in the entity graph might not be necessary.
//                                @NamedAttributeNode("orderNumber"),
//                                @NamedAttributeNode("orderDate"),
//                                @NamedAttributeNode("orderItems")
//                        }
//                )
//        }
)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Double price;

    @Basic(fetch = FetchType.LAZY)
    private Integer quantity;

    /**
     * The reason for puttign @JsonIgnore : If you remove this annotation, then while saving the Order,
     * When you serialize an Order object, it tries to serialize its associated OrderItem objects, which in turn reference the parent Order object, leading to a cyclic reference.
     * As as result we will get, equest processing failed: org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)] with root cause
     * java.lang.StackOverflowError: null
     */
    @ManyToOne
//    @ManyToOne(fetch = FetchType.LAZY) // Default behaviour is eager for ManyToOne if you override fetch type to lazy, you will see two queries, else only one query in the console after enabling hibernate logs.
//    @JoinColumn(name = "order_id") // This is the name of order column inside Order_Item table. We need to set each order explicitly on orderItems field of order entity
    @JsonIgnore
    private Order order;
}

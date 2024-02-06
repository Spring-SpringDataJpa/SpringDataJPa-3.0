package com.demo.jpa.nonreactive.repository;

import com.demo.jpa.nonreactive.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
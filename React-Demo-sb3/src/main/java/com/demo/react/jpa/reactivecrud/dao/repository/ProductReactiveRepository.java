package com.demo.react.jpa.reactivecrud.dao.repository;

import com.demo.react.jpa.reactivecrud.dao.entity.Product;
import com.demo.react.jpa.reactivecrud.dto.ProductDto;
import org.springframework.data.domain.Range;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/*
    @Repository plays a less significant role for reactive repositories.
 */
//@Repository
public interface ProductReactiveRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);
}


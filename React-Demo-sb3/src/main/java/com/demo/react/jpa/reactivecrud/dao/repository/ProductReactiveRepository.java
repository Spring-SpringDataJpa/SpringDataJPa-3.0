package com.demo.react.jpa.reactivecrud.dao.repository;

import com.demo.react.jpa.reactivecrud.dao.entity.Product;
import com.demo.react.jpa.reactivecrud.dto.ProductDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/*
    @Repository plays a less significant role for reactive repositories.
 */
//@Repository
public interface ProductReactiveRepository extends ReactiveCrudRepository<Product, UUID> {

    //R2DBC doesn't directly support Range arguments for query methods.
    //Provide explicit boundaries using separate arguments:
//    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);

    Flux<ProductDto> findProductsByPriceBetween(double minPrice, double maxPrice);
}


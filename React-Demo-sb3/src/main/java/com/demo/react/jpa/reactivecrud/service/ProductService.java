package com.demo.react.jpa.reactivecrud.service;

import com.demo.react.jpa.reactivecrud.common.AppUtils;
import com.demo.react.jpa.reactivecrud.dao.entity.Product;
import com.demo.react.jpa.reactivecrud.dao.repository.ProductReactiveRepository;
import com.demo.react.jpa.reactivecrud.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Flux: It is equivalent to List in non reactive programming
 * Mono: It is equivalent to single object in non reactive programming
 */
@Service
@Slf4j
//@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductReactiveRepository reactiveCrudRepository;

    public Flux<ProductDto> getProductsAsPureflux() {
        final Flux<ProductDto> productDtoFlux = reactiveCrudRepository.findAll().map(AppUtils::entityToDto);
        return productDtoFlux;
    }

    public Flux<List<ProductDto>> getProductsAsListOfFlux() {
        final Flux<ProductDto> productDtoFlux = reactiveCrudRepository.findAll().map(AppUtils::entityToDto);
        final List<ProductDto> collect = productDtoFlux.toStream().collect(Collectors.toList());
        final Mono<List<ProductDto>> fluxList = Mono.just(collect);
        return Flux.just(collect);
    }

    public Mono<List<ProductDto>> getProductsAsListOfMono() {
        final Flux<ProductDto> productDtoFlux = reactiveCrudRepository.findAll().map(AppUtils::entityToDto);
        final List<ProductDto> collect = productDtoFlux.toStream()
                .collect(Collectors.toList());
        final Mono<List<ProductDto>> monoList = Mono.just(collect);
        return monoList;
    }

    /**
     * It is not correct to return Mono in this case as findProductsByPriceBetweenis returning a list of ProductDto.
     * And also to not to use Flux<List<ProductDto></ProductDto>> findProductsByPriceBetween(double minPrice, double maxPrice);
     * But just ing "Flux<ProductDto>" as the return type is enough as Flux itself denotes list of ProductDto.
     *
     * // Read this
     * Flux Represents Streams: It's a reactive type specifically designed to handle asynchronous streams of data.
     * Single ProductDto Elements: Each element in the Flux represents an individual ProductDto object.
     * Unnecessary Nesting: Using Flux<List<ProductDto>> would create an extra level of nesting, as each element in the Flux would be a list containing a single ProductDto. This is redundant and less efficient.
     * Key Advantages of Flux<ProductDto>:
     *
     * Direct Data Handling: Subscribers can directly receive and process individual ProductDto objects as they become available.
     * Optimized Performance: Avoids unnecessary list creation and memory overhead, leading to better performance.
     * Conforms to Reactive Principles: Aligns with the reactive programming paradigm by using reactive types for handling asynchronous data flows.
     *
     */
//    public Mono<ProductDto> getProductInRange(double min, double max) {
////        return reactiveCrudRepository.findByPriceBetween(Range.closed(min, max));
//        final Mono<ProductDto> productDtoMono = reactiveCrudRepository
//                .findProductsByPriceBetween(min, max)
//                .single();
//        return productDtoMono;
//    }

    public Flux<ProductDto> getFluxOfProductsInRange(double min, double max) {
//        return reactiveCrudRepository.findByPriceBetween(Range.closed(min, max));
        final Flux<ProductDto> productDtoMono = reactiveCrudRepository
                .findProductsByPriceBetween(min, max);
        return productDtoMono;
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono) {
        final Mono<Product> productMono = productDtoMono.map((AppUtils::dtoToEntity));
        log.info("Saving product : {}", productMono);
        final Mono<Product> productMono2 = productMono.flatMap(reactiveCrudRepository::save);
        final Mono<ProductDto> productMono3 = productMono2.map(AppUtils::entityToDto);
        final Mono<ProductDto> productMonoDto = productMono3.single();

//       Same thing as above can be done in a fluent builder pattern
//        final Mono<ProductDto> dtoMono = productDtoMono
//                .map((AppUtils::dtoToEntity))
//                .flatMap(reactiveCrudRepository::save)  // Save and return Mono<Product>
//                .map(AppUtils::entityToDto)
//                .single();
        return productMonoDto;
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, UUID id) {
        return reactiveCrudRepository.findById(id)
//                .switchIfEmpty(Mono.error(() -> new ProductNotFoundException(id))) //FIXME Check why this line is tworing error
                .zipWith(productDtoMono)  // Corrected line: removed type parameter
                .map(tuple -> {  // Combine existing and new data within map
                    Product existingProduct = tuple.getT1();
                    ProductDto newProductDto = tuple.getT2();
                    BeanUtils.copyProperties(newProductDto, existingProduct);
                    return existingProduct;
                })
                .flatMap(reactiveCrudRepository::save)
                .map(AppUtils::entityToDto);
    }

//    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, UUID id) {
//        final Mono<Product> productMono = reactiveCrudRepository.findById(id);
//        final Mono<ProductDto> productMono2 = productMono.map(AppUtils::entityToDto);
//        log.info("---------ProuctMono3-------- :"+ productMono2);
//        final Mono<ProductDto> productMono3 = productMono2.doOnNext(e -> e.setId(id));
//        final Mono<ProductDto> productMonoDto = productMono3.flatMap(e -> saveProduct(productDtoMono));
////        final Mono<ProductDto> dtoMono = reactiveCrudRepository.findById(id).map(AppUtils::entityToDto)
////                .doOnNext(e -> e.setId(id))
////                .flatMap(e -> saveProduct(productDtoMono));
//        return productMonoDto;
//    }

    public Mono<Void> deleteProduct(UUID id) {
        final Mono<Void> voidMono = reactiveCrudRepository.deleteById(id);
        return voidMono;
    }
}
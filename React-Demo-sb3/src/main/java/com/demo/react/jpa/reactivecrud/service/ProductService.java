package com.demo.react.jpa.reactivecrud.service;

import com.demo.react.jpa.reactivecrud.common.AppUtils;
import com.demo.react.jpa.reactivecrud.dao.entity.Product;
import com.demo.react.jpa.reactivecrud.dao.repository.ProductReactiveRepository;
import com.demo.react.jpa.reactivecrud.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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

    public Mono<ProductDto> getProductInRange(double min, double max) {
//        return reactiveCrudRepository.findByPriceBetween(Range.closed(min, max));
        final Mono<ProductDto> productDtoMono = reactiveCrudRepository
                .findByPriceBetween(Range.closed(min, max))
                .single();
        return productDtoMono;
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono) {
        final Mono<Product> productMono = productDtoMono.map((AppUtils::dtoToEntity));
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

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, Long id) {
        final Mono<Product> productMono = reactiveCrudRepository.findById(id);
        final Mono<ProductDto> productMono2 = productMono.map(AppUtils::entityToDto);
        final Mono<ProductDto> productMono3 = productMono2.doOnNext(e -> e.setId(id));
        final Mono<ProductDto> productMonoDto = productMono3.flatMap(e -> saveProduct(productDtoMono));
//        final Mono<ProductDto> dtoMono = reactiveCrudRepository.findById(id).map(AppUtils::entityToDto)
//                .doOnNext(e -> e.setId(id))
//                .flatMap(e -> saveProduct(productDtoMono));
        return productMonoDto;
    }

    public Mono<Void> deleteProduct(Long id) {
        final Mono<Void> voidMono = reactiveCrudRepository.deleteById(id);
        return voidMono;
    }
}
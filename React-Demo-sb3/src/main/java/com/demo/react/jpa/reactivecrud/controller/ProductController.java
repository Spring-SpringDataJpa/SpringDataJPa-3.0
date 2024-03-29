package com.demo.react.jpa.reactivecrud.controller;

import com.demo.react.jpa.reactivecrud.dto.ProductDto;
import com.demo.react.jpa.reactivecrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Mono<ProductDto>> saveProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return new ResponseEntity<Mono<ProductDto>>(productService.saveProduct(productDtoMono), HttpStatus.CREATED);
    }

    @GetMapping("/product-asPureflux")
    public ResponseEntity<Flux<ProductDto>> getProductsAsPureflux() {
        return new ResponseEntity<Flux<ProductDto>>(productService.getProductsAsPureflux(), HttpStatus.CREATED);
    }

    @GetMapping("/product-asListOfFlux")
    public ResponseEntity<Flux<List<ProductDto>>> getProductsAsListOfFlux() {
        return new ResponseEntity<Flux<List<ProductDto>>>(productService.getProductsAsListOfFlux(), HttpStatus.CREATED);
    }

    @GetMapping("/product-asListOfMono")
    public ResponseEntity<Mono<List<ProductDto>>> getProductsAsListOfMono() {
        return new ResponseEntity<Mono<List<ProductDto>>>(productService.getProductsAsListOfMono(), HttpStatus.CREATED);
    }

    @GetMapping("/product-range")
    public ResponseEntity<Flux<ProductDto>> getProductInRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return new ResponseEntity<Flux<ProductDto>>(productService.getFluxOfProductsInRange(min, max), HttpStatus.CREATED);
    }

    // FIXME not working as expected
    @PutMapping("/update/{id}")
    public ResponseEntity<Mono<ProductDto>> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable("id") UUID id) {
        return new ResponseEntity<Mono<ProductDto>>(productService.updateProduct(productDtoMono, id), HttpStatus.CREATED);
    }

    //FIXME Not working as expected
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") UUID id) {
        return productService.deleteProduct(id);
    }
}

package com.demo.react.jpa.reactivecrud.common;

import com.demo.react.jpa.reactivecrud.dao.entity.Product;
import com.demo.react.jpa.reactivecrud.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

import static java.util.UUID.randomUUID;


@Slf4j
public class AppUtils {

    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        final UUID uuid = randomUUID();
        log.info("-------------Generated UUID---------------" + uuid);
        product.setId(uuid);
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}

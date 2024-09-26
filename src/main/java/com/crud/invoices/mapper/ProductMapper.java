package com.crud.invoices.mapper;

import com.crud.invoices.domain.Product;
import com.crud.invoices.domain.ProductDto;
import com.crud.invoices.domain.ProductOutgoingDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {


    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getNameOfProduct(),
                product.getType(),
                product.getUnit(),
                product.getCode(),
                product.getNetWorth(),
                product.getVatRate(),
                product.getGrossValue()
                //product.getProductsItems()
        );
    }


    public Product mapToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getNameOfProduct(),
                productDto.getType(),
                productDto.getUnit(),
                productDto.getCode(),
                productDto.getNetWorth(),
                productDto.getVatRate(),
                productDto.getGrossValue()

        );
    }

    public List<ProductDto> mapToProductsDtoList(final List<Product>productList) {
        return productList.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getNameOfProduct(),
                        product.getType(),
                        product.getUnit(),
                        product.getCode(),
                        product.getNetWorth(),
                        product.getVatRate(),
                        product.getGrossValue()
                )).collect(Collectors.toList());
    }


    public ProductOutgoingDto mapToProductOutgoingDto(Product product) {
        return new ProductOutgoingDto(
                product.getId(),
                product.getNameOfProduct(),
                product.getType(),
                product.getUnit(),
                product.getCode(),
                product.getNetWorth(),
                product.getVatRate(),
                product.getGrossValue()
        );
    }

    public List<ProductOutgoingDto> mapToProductsOutgoingDtoList(final List<Product>productList) {
        return productList.stream()
                .map(product -> new ProductOutgoingDto(
                        product.getId(),
                        product.getNameOfProduct(),
                        product.getType(),
                        product.getUnit(),
                        product.getCode(),
                        product.getNetWorth(),
                        product.getVatRate(),
                        product.getGrossValue()
                )).collect(Collectors.toList());
    }
}

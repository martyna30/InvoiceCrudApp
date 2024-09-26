package com.crud.invoices.controller;

import com.crud.invoices.domain.ListProductsDto;
import com.crud.invoices.domain.ProductDto;
import com.crud.invoices.mapper.ProductMapper;
import com.crud.invoices.service.ProductService;
import com.crud.invoices.validationGroup.OrderChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RequestMapping("/v1/product")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ValidationErrors validationErrors;



    @PostMapping(value = "createProduct", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createProduct(@Validated(value = {OrderChecks.class})
                                                @Valid @RequestBody ProductDto productDto,
                                                Errors errors) throws MethodArgumentNotValidException {
        if (errors.hasErrors()) {
            return validationErrors.checkErrors(errors);
        }
        productService.saveProduct(productMapper.mapToProduct(productDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "updateProduct")
    public ResponseEntity<Object> updateProduct(@Validated(value = {OrderChecks.class}) @Valid @RequestBody ProductDto productDto, Errors errors) throws ProductNotFoundException {
        if (errors.hasErrors()) {
                return validationErrors.checkErrors(errors);
        }
        productMapper.mapToProductOutgoingDto(productService.saveProduct(productMapper.mapToProduct(productDto)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }





    @GetMapping(value = "getProduct")
    public ProductDto getProduct(@RequestParam Long productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productService.getProduct(productId)
                .orElseThrow(ProductNotFoundException::new));
    }


    @GetMapping(value = "getProducts")
    public ResponseEntity<ListProductsDto> getProducts(@RequestParam int page, @RequestParam int size) throws ResponseStatusException {
        PageRequest pageRequest = PageRequest.of(page, size);
        try {
            return ResponseEntity.ok(new ListProductsDto(productService.getCount(),
                    productMapper.mapToProductsOutgoingDtoList(productService.getAllProducts(pageRequest))));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @DeleteMapping(value = "deleteProduct", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
         try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
         }
    }




}






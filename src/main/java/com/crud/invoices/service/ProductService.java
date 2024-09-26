package com.crud.invoices.service;

import com.crud.invoices.domain.Product;
import com.crud.invoices.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    @Transactional
    public Product saveProduct(final Product product) {
        return productRepository.findProductByNameOfProduct(product.getNameOfProduct())
                .map(existingProduct -> {
                    updateProduct(existingProduct, product);
                    return productRepository.save(existingProduct);
                })
                .orElseGet(() -> productRepository.save(product));
    }

    private void updateProduct(Product existingProduct, Product product) {
        existingProduct.setNameOfProduct(product.getNameOfProduct());
        existingProduct.setType(product.getType());
        existingProduct.setCode(product.getCode());
        existingProduct.setNetWorth(product.getNetWorth());
        existingProduct.setVatRate(product.getVatRate());
        existingProduct.setGrossValue(product.getGrossValue());
    }



    public Optional<Product> getProduct(final Long productId) {
        return productRepository.findById(productId);
    }


    public List<Product> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.getContent();
    }

    public long getCount() {
        return productRepository.count();
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


}

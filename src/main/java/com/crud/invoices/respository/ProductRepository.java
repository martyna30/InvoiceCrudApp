package com.crud.invoices.respository;


import com.crud.invoices.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface ProductRepository extends CrudRepository<Product, Long>,
        PagingAndSortingRepository<Product, Long> {


    @Override
    Product save(Product product);



    @Override
    Optional<Product> findById(Long productId);

    @Override
    void deleteById(Long productId);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Override
    long count();


    Optional<Product> findProductByNameOfProduct(String name);
}

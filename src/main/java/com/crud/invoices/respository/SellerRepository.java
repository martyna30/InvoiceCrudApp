package com.crud.invoices.respository;

import com.crud.invoices.domain.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface SellerRepository extends CrudRepository<Seller, Long> {

    @Override
    Seller save(Seller seller);

    @Override
    Optional<Seller> findById(Long id);

    @Override
    void deleteById(Long id);

    Optional<Seller> findSellerByVatIdentificationNumber(String vatIdentificationNumber);

    List<Seller> findByNameContainsIgnoreCase(String name);

    Optional<Seller> findSellerByNameIgnoreCase(String name);


    Optional<Seller> findSellerByAppUser_Username(String username);


}

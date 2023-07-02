package com.crud.invoices.respository;

import com.crud.invoices.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    @Override
    Address save(Address address);

    @Override
    Optional<Address> findById(Long id);

    @Override
    void deleteById(Long id);


}

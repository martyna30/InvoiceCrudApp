package com.crud.invoices.respository;

import com.crud.invoices.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Override
    List<Customer> findAll();

    @Override
    Customer save(Customer customer);

    @Override
    Optional<Customer> findById(Long id);

    @Override
    void deleteById(Long id);
}

package com.crud.invoices.respository;

import com.crud.invoices.domain.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ExchangeRepository extends CrudRepository<Exchange, Long> {

    @Override
    List<Exchange> findAll();

    @Override
    Optional<Exchange> findById(Long id);

    @Override
    Exchange save(Exchange exchange);

    List<Exchange> findByDate(Date date);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}

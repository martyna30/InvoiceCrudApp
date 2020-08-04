package com.crud.invoices.respository;

import com.crud.invoices.domain.Exchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ExchangeRepository extends CrudRepository<Exchange, Long> {

    @Override
    List<Exchange> findAll();

    @Override
    Exchange save(Exchange exchange);

    @Override
    void deleteById(Long id);

    @Query
    List<Exchange> retrieveTableWithSpecifiedDate(@Param("EFFECTIVE_DATE") LocalDate date);

    @Override
    long count();

}

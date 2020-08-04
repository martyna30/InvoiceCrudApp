package com.crud.invoices.respository;

import com.crud.invoices.domain.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CurrencyRepository extends CrudRepository<Rate, Long> {
    @Override
    List<Rate> findAll();

    @Override
    Rate save(Rate currency);

    @Override
    Optional<Rate> findById(Long id);

    @Override
    void deleteById(Long id);
}

package com.crud.invoices.respository;

import com.crud.invoices.domain.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {
    @Override
    List<Rate> findAll();

    @Override
    Optional<Rate> findById(Long id);

    @Query("SELECT r FROM Rate r WHERE r.currency = :currency AND r IN (SELECT rates FROM Exchange e JOIN e.rates rates WHERE e.date = :effectiveDate)")
    Optional<Rate> findByCurrencyAndEffectiveDate(@Param("currency")String currency, @Param("effectiveDate") LocalDate date);


}

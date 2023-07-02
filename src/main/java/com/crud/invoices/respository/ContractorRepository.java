package com.crud.invoices.respository;

import com.crud.invoices.domain.Contractor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface ContractorRepository extends CrudRepository<Contractor, Long>,
        PagingAndSortingRepository<Contractor,Long> {
    @Override
    Page<Contractor> findAll(Pageable pageable);

    @Override
    Contractor save(Contractor contractor);

    @Override
    Optional<Contractor> findById(Long id);

    @Override
    void deleteById(Long id);

    Optional<Contractor> findContractorByVatIdentificationNumber(String vatIdentificationNumber);
}

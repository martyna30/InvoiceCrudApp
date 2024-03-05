package com.crud.invoices.respository;

import com.crud.invoices.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long>,
        PagingAndSortingRepository<Invoice, Long> {

    @Override
    Invoice save(Invoice invoice);

    @Override
    Optional<Invoice> findById(Long id);
    @Override
    void deleteById(Long id);
    @Override
    Page<Invoice> findAll(Pageable pageable);

    //List<Item> findItemsByInvoiceId(Long invoiceId);

    @Override
    long count();

}

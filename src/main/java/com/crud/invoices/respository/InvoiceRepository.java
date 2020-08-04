package com.crud.invoices.respository;

import com.crud.invoices.domain.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
    @Override
    List<Invoice> findAll();

    @Override
    Invoice save(Invoice invoice);

    @Override
    Optional<Invoice> findById(Long id);

    @Override
    void deleteById(Long id);

    //@Override
    //Invoice invoice findInvoiceByNumber();
}

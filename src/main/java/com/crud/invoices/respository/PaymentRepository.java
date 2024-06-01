package com.crud.invoices.respository;

import com.crud.invoices.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Override
    Payment save(Payment payment);

    @Override
    Optional<Payment> findById(Long id);


    List<Payment> findAllPaymentsByInvoiceId(Long invoiceId);

    @Override
    boolean existsById(Long id);

    @Override
    List<Payment> findAll();

    @Override
    long count();

    @Override
    void deleteById(Long paymentId);

}

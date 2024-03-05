package com.crud.invoices.respository;

import com.crud.invoices.domain.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ItemRespository extends CrudRepository<Item, Long> {
    @Override
    List<Item> findAll();

    @Query("FROM Item WHERE invoice_id = :INVOICE_ID")
    List<Item> findItemsByInvoice(@Param("INVOICE_ID")Long invoice_Id);
    @Override
    Item save(Item item);

    @Override
    Optional<Item> findById(Long id);


    @Override
    void deleteById(Long id);
}

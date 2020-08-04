package com.crud.invoices.mapper;

import com.crud.invoices.domain.Customer;
import com.crud.invoices.domain.CustomerDto;
import com.crud.invoices.domain.Seller;
import com.crud.invoices.domain.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {
    @Autowired
    InvoiceMapper invoiceMapper;

    public Seller mapToSeller(final SellerDto sellerDto) {
        return new Seller(
                sellerDto.getName()

        );
    }

    public SellerDto mapToSellerDto(final Seller seller) {
        return new SellerDto(
                seller.getId(),
                seller.getName()

        );

    }
}

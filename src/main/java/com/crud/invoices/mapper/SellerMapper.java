package com.crud.invoices.mapper;

import com.crud.invoices.domain.Customer;
import com.crud.invoices.domain.CustomerDto;
import com.crud.invoices.domain.Seller;
import com.crud.invoices.domain.SellerDto;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {
    public Seller mapToSeller(final SellerDto sellerDto) {
        return new Seller(
                sellerDto.getId(),
                sellerDto.getName(),
                sellerDto.getInvoices()
        );
    }

    public SellerDto mapToSellerDto(final Seller seller) {
        return new SellerDto(
                seller.getId(),
                seller.getName(),
                seller.getInvoices()
        );
    }
}

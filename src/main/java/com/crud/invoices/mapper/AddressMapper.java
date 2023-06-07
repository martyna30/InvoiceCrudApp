package com.crud.invoices.mapper;

import com.crud.invoices.domain.Address;
import com.crud.invoices.domain.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {


    public AddressDto mapToAddressDto(Address address) {
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getPostcode(),
                address.getCity());
    }

    public Address mapToAddress(AddressDto addressDto) {
        return new Address(
                addressDto.getId(),
                addressDto.getStreet(),
                addressDto.getStreetNumber(),
                addressDto.getPostcode(),
                addressDto.getCity());
    }
}

package com.crud.invoices.mapper;

import com.crud.invoices.domain.Seller;
import com.crud.invoices.domain.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {
    @Autowired
    AddressMapper adressMapper;
    @Autowired
    UserMapper userMapper;


    public SellerDto mapToSellerDto(Seller seller) {
        return new SellerDto(
                seller.getId(),
                seller.getName(),
                seller.getVatIdentificationNumber(),
                adressMapper.mapToAddressDto(seller.getAddress()),
                userMapper.mapToUserDto(seller.getAppUser())
                );
    }

    public Seller mapToSeller(SellerDto sellerDto) {
        return new Seller(
                sellerDto.getId(),
                sellerDto.getName(),
                sellerDto.getVatIdentificationNumber(),
                adressMapper.mapToAddress(sellerDto.getAddressDto()),
                userMapper.mapToUser(sellerDto.getAppUserDto()));
    }
}

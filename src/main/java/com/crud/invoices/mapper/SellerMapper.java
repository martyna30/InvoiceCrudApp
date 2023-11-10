package com.crud.invoices.mapper;

import com.crud.invoices.domain.*;
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

    public SellerDto mapToSellerDtoFromGus(ContractorFromGusDto contractorFromGusDto) {
       return new SellerDto(
               contractorFromGusDto.getId(),
               contractorFromGusDto.getName(),
               contractorFromGusDto.getVatIdentificationNumber(),
               new AddressDto(
                       null,
                       contractorFromGusDto.getStreet(),
                       contractorFromGusDto.getStreetNumber(),
                       contractorFromGusDto.getPostcode(),
                       contractorFromGusDto.getCity(),
                       null
               ),
               null
       );
    }
}

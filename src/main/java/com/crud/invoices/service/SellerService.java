package com.crud.invoices.service;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.Seller;
import com.crud.invoices.respository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    UserService userService;



    public Seller saveSeller(final Seller seller) {
        Optional<Seller> sellerInDatabase = this.sellerRepository.findSellerByVatIdentificationNumber(seller.getVatIdentificationNumber());
        if (sellerInDatabase.isPresent()) {
            seller.setId(sellerInDatabase.get().getId());
            seller.getAddress().setId(sellerInDatabase.get().getAddress().getId());
            sellerInDatabase.get().setName(seller.getName());
            Optional<AppUser> appUserInDatabase = userService.getAppUserByUsername(seller.getAppUser().getUsername());
            if(appUserInDatabase.isPresent()) {
                seller.setAppUser(appUserInDatabase.get());
            }
        }
        else {
            Optional<AppUser> appUserInDatabase = userService.getAppUserByUsername(seller.getAppUser().getUsername());
            if(appUserInDatabase.isPresent()) {
                seller.setAppUser(appUserInDatabase.get());
            }
        }
        return sellerRepository.save(seller);
    }


    public Optional<Seller> getSeller(final Long id) {
        return sellerRepository.findById(id);
    }


    public void deleteSeller(final Long id) {
        sellerRepository.deleteById(id);
    }


    public Optional<Seller> getSellerByName(String name) {
        Optional<Seller> sellerWithName = sellerRepository.findSellerByNameIgnoreCase(name);
        return Optional.ofNullable(sellerWithName
                .orElseThrow(() -> new RuntimeException("Seller doesn't exist")));
    }

    public Optional<Seller>  getSellerByVatIdentificationNumber(String vatIdentificationNumber) {
        return sellerRepository.findSellerByVatIdentificationNumber(vatIdentificationNumber);
    }

    public Seller getSellerByAppUser(String username) {
        Optional<Seller> sellerForAppUser = sellerRepository.findSellerByAppUser_Username(username);
        return Optional.ofNullable(sellerForAppUser.orElseThrow(() ->new RuntimeException("Seller doesn't exist"))).get();
    }
}

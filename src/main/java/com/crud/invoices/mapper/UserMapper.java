package com.crud.invoices.mapper;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.AppUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    @Autowired
    SellerMapper sellerMapper;
    public AppUser mapToUser(AppUserDto appUserDto) {
        return new AppUser(
                appUserDto.getUsername(),
                appUserDto.getPassword(),
                appUserDto.getEmail(),
                appUserDto.getRole()

        );
    }

    public AppUserDto mapToUserDto(AppUser appUser) {
        return new AppUserDto(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getEmail(),
                appUser.getRole()
        );

    }

}

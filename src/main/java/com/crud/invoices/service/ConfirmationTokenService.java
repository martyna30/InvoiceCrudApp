package com.crud.invoices.service;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.ConfirmationToken;
import com.crud.invoices.respository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {
    @Autowired
    UserService userService;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getConfirmationTokenForUser(AppUser appUser) {
        List<ConfirmationToken> confirmationTokenForUser = confirmationTokenRepository.findByUserId(appUser.getId());
        Optional<ConfirmationToken>confirmationTokeWithConfirmed = confirmationTokenForUser.stream().
                filter(confirmationToken -> confirmationToken.getConfirmedAt()!=null).findAny();
        if(confirmationTokeWithConfirmed.isPresent()) {
            return Optional.ofNullable(confirmationTokeWithConfirmed.get());
            //.orElseThrow(() -> new RuntimeException("Confirmation token doesn't exist")));
        }
       return Optional.empty();
    }
    public Optional<ConfirmationToken> getToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        return Optional.ofNullable(confirmationToken.orElseThrow(() -> new RuntimeException("Token doesn't exist")));
    }

    public void deleteToken(final Long id) {
        confirmationTokenRepository.deleteById(id);
    }

    public List<ConfirmationToken> getAll() {
        return confirmationTokenRepository.findAll();
    }






}

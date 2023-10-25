package com.crud.invoices.service;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.ConfirmationToken;
import com.crud.invoices.respository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {

    UserService userService;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getConfirmationTokenForUser(AppUser appUser) {
        Optional<ConfirmationToken> confirmationTokenForUser = confirmationTokenRepository.findByUserId(appUser.getId());
        if(confirmationTokenForUser.isPresent()) {
            return Optional.ofNullable(confirmationTokenForUser.orElseThrow(() -> new RuntimeException("Confirmation token doesn't exist")));
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

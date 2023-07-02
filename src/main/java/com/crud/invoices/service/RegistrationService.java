package com.crud.invoices.service;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.AppUserRole;
import com.crud.invoices.domain.ConfirmationToken;
import com.crud.invoices.mail.EmailService;
import com.crud.invoices.registration.RegisterCredentialsDto;
import com.crud.invoices.respository.UserRepository;
import com.crud.invoices.validation.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private EmailService emailService;

    public String register(RegisterCredentialsDto registerCredentials) throws MessagingException {
        boolean isValidEmail = emailValidator.test(registerCredentials.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        String token = userService.signUpUser(
                new AppUser(
                        registerCredentials.getUsername(),
                        registerCredentials.getPassword(),
                        registerCredentials.getEmail(),
                        AppUserRole.USER
                )
        );
        String link = "http://localhost:8080/v1/library/register/confirm?token=" + token;
        emailService.send(registerCredentials.getEmail(), link);
        return token;
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->
                    new IllegalStateException("token not found"));
        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if(expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        AppUser appUser = userRepository.findById(confirmationToken.getAppUser().getId()).get();
        appUser.setEnabled(true);
        userService.saveUser(appUser);
        return "Token confirmed";
    }
}


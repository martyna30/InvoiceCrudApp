package com.crud.invoices.validation;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.ConfirmationToken;
import com.crud.invoices.registration.RegisterCredentialsDto;
import com.crud.invoices.respository.UserRepository;
import com.crud.invoices.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UserValidator implements ConstraintValidator<UserConstraint, RegisterCredentialsDto> {
    private String field;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Override
    public void initialize(UserConstraint userConstraint) {
        this.field = userConstraint.field();
    }

    @Override
    public boolean isValid(final RegisterCredentialsDto registerCredentialsDto, final ConstraintValidatorContext context) {
        Optional<AppUser> users = userRepository.findByEmail(registerCredentialsDto.getEmail());
        if(users.isPresent()) {
            Optional<ConfirmationToken> confirmationTokeWithConfirmed = confirmationTokenService.getConfirmationTokenForUser(users.get());
            if (confirmationTokeWithConfirmed.isPresent()) {
                //throw new IllegalStateException("User with such a email already exist");
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(field)
                        .addConstraintViolation();
                return false;
            }
            return true;
        }
        return true;
    }
}

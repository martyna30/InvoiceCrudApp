package com.crud.invoices.registration;

import com.crud.invoices.validation.UserConstraint;
import com.crud.invoices.validationGroup.LengthOfCharacters;
import com.crud.invoices.validationGroup.NotEmptyGroup;
import com.crud.invoices.validationGroup.UniqueFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@UserConstraint(groups = UniqueFormat.class, field = "email")
public class RegisterCredentialsDto {
    @NotBlank(groups= NotEmptyGroup.class, message = "Please enter your username")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length username must be between 3 and 30 characters")
    private String username;
    @NotBlank(groups= NotEmptyGroup.class, message =" Please enter your password")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length password must be between 3 and 30 characters")
    private String password;
    private String email;
    private String nip;
}

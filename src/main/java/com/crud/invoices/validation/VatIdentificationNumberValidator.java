package com.crud.invoices.validation;

import com.crud.invoices.domain.Contractor;
import com.crud.invoices.domain.ContractorDto;
import com.crud.invoices.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class VatIdentificationNumberValidator implements ConstraintValidator<VatIdentificationNumberConstraint, ContractorDto> {
    private String field;

    @Autowired
    ContractorService contractorService;


    @Override
    public void initialize(VatIdentificationNumberConstraint vatIdentificationNumberConstraint) {
        this.field = vatIdentificationNumberConstraint.field();
    }

    @Override
    public boolean isValid(final ContractorDto contractorDto, final ConstraintValidatorContext context) {
        Optional<Contractor> contractorFromDB = contractorService.findContractorByVatIdentificationNumber(contractorDto.getVatIdentificationNumber());
        if (contractorFromDB.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(field)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

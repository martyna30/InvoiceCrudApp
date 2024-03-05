package com.crud.invoices.validationGroup;

//import com.library.validation.SignatureValidator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value={NotEmptyGroup.class, LengthOfCharacters.class, Format.class, Default.class})
    public interface OrderChecks {}
// LengthOfCharacters.class, UniqueFormat.class,  Default.class


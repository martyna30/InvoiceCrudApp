package com.crud.invoices.validationGroup;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value={NotEmptyGroup.class, Format.class, LengthOfCharacters.class, Settled.class, Default.class })
public interface OrderChecks3 {
}

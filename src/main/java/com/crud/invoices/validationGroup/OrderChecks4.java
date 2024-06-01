package com.crud.invoices.validationGroup;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value={NotEmptyGroup.class,Format.class, Default.class})
public interface OrderChecks4 {



}

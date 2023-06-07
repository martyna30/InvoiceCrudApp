package com.crud.invoices.nbp.validator;

import com.crud.invoices.domain.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Component
public class NBPvalidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(NBPvalidator.class);

    public List<Exchange> validateTable(final List<Exchange> tableNBP) {
        LOGGER.info("Starting to check table...");
        List<Exchange> currentTable = tableNBP.stream()
                .filter(currentNBPtable -> currentNBPtable.getDate() == LocalDate.now())
                .collect(toList());
        LOGGER.info("Table has been filtered. Current table: " + currentTable.size());
        return ofNullable(currentTable).orElse(null);
    }
}

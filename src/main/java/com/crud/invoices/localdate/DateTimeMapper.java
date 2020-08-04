package com.crud.invoices.localdate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Configuration
public class DateTimeMapper {
    public static final DateTimeFormatter FORMATTER = ofPattern("dd::MM::yyyy");

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        //javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}

package com.crud.invoices.controller;

import com.crud.invoices.domain.Exchange;
import com.crud.invoices.domain.ExchangeDto;
import com.crud.invoices.domain.Rate;
import com.crud.invoices.domain.RateDto;
import com.crud.invoices.mapper.ExchangeMapper;
import com.crud.invoices.nbp.facade.ExchangeFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeController.class)
//udostępnienie controllera do testowania,Dzięki adnotacji @WebMvcTest możemy również korzystać z MockMvc
public class ExchangeControllerTest {
    @Autowired
    private MockMvc mockMvc;//MockMvc - jest to klasa pozwalająca na wykonywanie żądań HTTP do naszego controllera

    @MockBean//Używamy jej gdy korzystamy z @RunWith(SpringRunner.class). Pozwala nam na dodanie zachowań klasie
    private ExchangeFacade exchangeFacade;


    @Test
    public void shouldFetchCurrencyExchangeRatesTable() throws Exception {
        //Given
        /*
        List<RateDto> rateDto = new ArrayList<>();
        rateDto.add(new RateDto("USD", new BigDecimal(3.8997)));

        List<Rate> rate = new ArrayList<>();
        rate.add(new Rate("USD", new BigDecimal(3.8997)));

        List<ExchangeDto> exchangeDto = new ArrayList<>();
        exchangeDto.add(new ExchangeDto("A", rateDto));

        List<Exchange> exchange = new ArrayList<>();
        exchange.add(new Exchange("A", rate));

        when(exchangeFacade.getCurrencyExchangeRatesTableAndSaveToDatabase()).thenReturn(exchangeDto);

        //When
        mockMvc.perform(get("/v1/nbp/getTable").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                //ExchangeDto fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].table", is("A")))
                //RateDto fields
                        .andExpect(jsonPath("$[0].rates", hasSize(1)))
                        .andExpect(jsonPath("$[0].rates[0].mid", is(new BigDecimal(3.8997))))
                .andExpect(jsonPath("$[0].rates[0].code", is("USD")));

         */
    }
}


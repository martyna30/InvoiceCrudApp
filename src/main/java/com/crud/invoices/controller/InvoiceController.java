package com.crud.invoices.controller;


import com.crud.invoices.domain.InvoiceComingDto;
import com.crud.invoices.domain.InvoiceOutgoingDto;
import com.crud.invoices.domain.ListInvoicesDto;
import com.crud.invoices.exception.InvoiceNullPointerException;
import com.crud.invoices.mapper.InvoiceMapper;
import com.crud.invoices.service.InvoiceService;
import com.crud.invoices.validationGroup.OrderChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceMapper invoiceMapper;


    @Autowired
    ValidationErrors validationErrors;

    @GetMapping(value = "getInvoices")
    public ResponseEntity<ListInvoicesDto> getInvoices(@RequestParam int page, @RequestParam int size) throws ResponseStatusException {
        PageRequest pageRequest = PageRequest.of(page, size);
        try {
            return ResponseEntity.ok(new ListInvoicesDto(invoiceService.getCount(),
                    invoiceMapper.mapToInvoiceOutgoingDtoList(invoiceService.getAllInvoices(pageRequest))));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @GetMapping(value = "getInvoice")
    public ResponseEntity<InvoiceOutgoingDto> getInvoice(@RequestParam Long invoiceId) {
        try {
            return ResponseEntity.ok(invoiceMapper.mapToInvoiceOutgoingDto(invoiceService.getInvoice(invoiceId).get()));
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "deleteInvoice", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteInvoice(@RequestParam Long invoiceId) {
        try {
            invoiceService.deleteInvoice(invoiceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "updateInvoice")
    public ResponseEntity<Object> updateInvoice(@Validated(value = {OrderChecks.class}) @Valid @RequestBody InvoiceComingDto invoiceDto, Errors errors) throws ContractorNotFoundException {

        if (errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        invoiceMapper.mapToInvoiceOutgoingDto(invoiceService.updateInvoiceWithContractor(invoiceMapper.mapToInvoice(invoiceDto)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*@PutMapping(value = "settleInvoice")
    public ResponseEntity<Object> settleInvoice(@Validated(value = {OrderChecks.class}) @Valid
                                                    @RequestBody InvoiceComingDto invoiceDto,
                                                @RequestParam  Long invoiceId, Errors errors) {
        if (errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        //invoiceMapper.mapToInvoiceOutgoingDto(invoiceService.settleInvoiceWithContractor(invoiceMapper.mapToInvoice(invoiceDto), invoiceId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/


    @PostMapping(value = "createInvoice", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createInvoice(@Validated(value = {OrderChecks.class})
                                                    @Valid @RequestBody InvoiceComingDto invoiceDto,
                                                Errors errors) throws InvoiceNullPointerException, ContractorNotFoundException {
        if (errors.hasErrors()) {
            return validationErrors.checkErrors(errors);
        }
        invoiceService.saveInvoiceWithContractor(invoiceMapper.mapToInvoice(invoiceDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "createInvoiceWithoutContractor", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createInvoiceWithoutContractor(@Validated(value = {OrderChecks.class}) @Valid @RequestBody InvoiceComingDto invoiceDto, Errors errors){
        if (errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
            invoiceService.saveInvoiceWithoutContractor(invoiceMapper.mapToInvoice(invoiceDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
    }


     /*@RequestMapping(method = RequestMethod.GET, value = "/exception2")
    public String getException1(ModelMap model, @RequestParam("p") String p, HttpServletRequest request) {

        System.out.println("Exception 2 " + p);
        request.setAttribute("p", p);
        throw new CustomGenericException("1", "2");


  @ExceptionHandler(NullPointerException.class)
   / public ModelAndView handleCustomException(NullPointerException ex, HttpServletRequest request) {

        ModelAndView model2 = new ModelAndView("error/generic_error");
        model2.addObject("exception", ex);
        System.out.println(request.getAttribute("p"));
        System.out.println("CustomGenericException  ");
        return model2;

    }*/
}








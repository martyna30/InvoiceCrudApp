package com.crud.invoices.mapper;

import com.crud.invoices.domain.Contractor;
import com.crud.invoices.domain.ContractorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ContractorMapper {

    @Autowired
    InvoiceMapper invoiceMapper;

    @Autowired
    AddressMapper adressMapper;

    /*public List<Contractor> mapToContractorList(final List<ContractorDto> contractorDtos) {
        List<Contractor> customers = new ArrayList<>();
        for (ContractorDto customerDto : contractorDtos) {
            Contractor contractor = new Contractor();
            //customer.setId(customerDto.getId());
            //contractor.setInvoices(invoiceMapper.mapToInvoiceList(customerDto.getInvoices()));
           // contractor.setName( customerDto.getName());
            //contractor.setVatNumber( customerDto.getVatNumber());
           // contractor.setVATpayer(customerDto.isVATpayer());
            //customers.add(customer);
        }
        return customers;
    }*/

    public ContractorDto mapToContractorDto(Contractor contractor) {
        return new ContractorDto(
                contractor.getId(),
                contractor.getName(),
                contractor.getVatIdentificationNumber(),
                adressMapper.mapToAddressDto(contractor.getAddress())
        );

    }

    public Contractor mapToContractor(ContractorDto contractorDto) {
        return new Contractor(
                contractorDto.getId(),
                contractorDto.getName(),
                contractorDto.getVatIdentificationNumber(),
                adressMapper.mapToAddress(contractorDto.getAddressDto()));
    }


    public List<ContractorDto> mapToContractorDtoList(final List<Contractor> contractors) {
        return contractors.stream()
                .map(contractor -> new ContractorDto(
                        contractor.getId(),
                        contractor.getName(),
                        contractor.getVatIdentificationNumber(),
                        adressMapper.mapToAddressDto(contractor.getAddress())
                ))
                .collect(toList());
    }

    public List<Contractor> mapToContractorList(final List<ContractorDto> contractorsDtos) {
        return contractorsDtos.stream()
                .map(contractorDto -> new Contractor(
                        contractorDto.getId(),
                        contractorDto.getName(),
                        contractorDto.getVatIdentificationNumber(),
                adressMapper.mapToAddress(contractorDto.getAddressDto())))
                .collect(toList());
    }

    /*public Customer mapToCustomer( final CustomerDto customerDto) {
        Customer customer = new Customer();

        //customer.setId(customerDto.getId());
        customer.setInvoices(invoiceMapper.mapToInvoiceList(customerDto.getInvoices()));
        customer.setName(customerDto.getName());
        customer.setVatNumber(customerDto.getVatNumber());
        customer.setVATpayer(customerDto.isVATpayer());
        return customer;
    }*/

    /*public CustomerDto mapToCustomerDto(final Customer customer) {
        return new CustomerDto(
                customer.getId(),
                invoiceMapper.mapToInvoiceDtoList(customer.getInvoices()),
                customer.getName(),
                customer.getVatNumber()
                , customer.isVATpayer());
    }*/
}

package com.crud.invoices.mapper;

import com.crud.invoices.client.bir.ReportParser;
import com.crud.invoices.domain.AddressDto;
import com.crud.invoices.domain.Contractor;
import com.crud.invoices.domain.ContractorDto;
import com.crud.invoices.domain.ContractorFromGusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class ContractorMapper {

    @Autowired
    InvoiceMapper invoiceMapper;

    @Autowired
    AddressMapper adressMapper;
    @Autowired
    ReportParser reportParser;

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

    public ContractorDto mapToContractorDtoFromGus(ContractorFromGusDto contractorFromGusDto) {
        return new ContractorDto(
                contractorFromGusDto.getId(),
                contractorFromGusDto.getName(),
                contractorFromGusDto.getVatIdentificationNumber(),
                new AddressDto(
                        null,
                        contractorFromGusDto.getStreet(),
                        contractorFromGusDto.getStreetNumber(),
                        contractorFromGusDto.getPostcode(),
                        contractorFromGusDto.getCity(),
                        null
                )
        );
    }

    public ContractorFromGusDto mapToContractorFromGusDto(Map<String, String> map) {
        String name = map.entrySet()
                .stream()
                .filter(s -> s.getKey().equals("Nazwa"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        String vatIdentificationNumber = map.entrySet()
                .stream()
                .filter(s -> s.getKey().equals("Nip"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());


        String street  = map.entrySet()
                .stream()
                .filter(s -> s.getKey().equals("Ulica"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        String streetNumber = map.entrySet()
                .stream()
                .filter(s -> s.getKey().equals("NrNieruchomosci"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        String postcode = map.entrySet()
                .stream()
                .filter(s -> s.getKey().equals("KodPocztowy"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        String city = map.entrySet()
                .stream()
                .filter(s -> s.getKey().equals("Miejscowosc"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        return new ContractorFromGusDto (
                null,
                name,
                vatIdentificationNumber,
                street,
                streetNumber,
                postcode,
                city
        );
    }
    /*
        ContractorFromGusDto contractorFromGusDto;
                map.entrySet().stream().map(

                        .filter(stringStringEntry -> stringStringEntry.getKey().equals("Nazwa"))
                                .


                contractorFromGusDto.setName();



        for(Map.Entry<String, String> maps : map.entrySet()) {
            if (maps.getKey().contains("Nazwa")) {
                contractorFromGusDto.setName(maps.getValue());
                System.out.println(contractorFromGusDto.getName());
            }
            if (maps.getKey().contains("Nip")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }
            if (maps.getKey().contains("Ulica")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("NrNieruchomosci")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("KodPocztowy")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }

            if (maps.getKey().contains("Miejscowosc")) {
                contractorFromGusDto.setVatIdentificationNumber(maps.getValue());
                System.out.println(contractorFromGusDto.getVatIdentificationNumber());
            }






                contractor.getId(),
                contractor.getName(),
                contractor.getVatIdentificationNumber(),
                adressMapper.mapToAddressDto(contractor.getAddress())
        );*/


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

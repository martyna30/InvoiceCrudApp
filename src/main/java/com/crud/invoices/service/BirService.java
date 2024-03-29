package com.crud.invoices.service;

import com.crud.invoices.client.bir.BirClient;
import com.crud.invoices.client.bir.RegonType;
import com.crud.invoices.client.bir.ReportClient;
import com.crud.invoices.client.bir.ReportParser;
import com.crud.invoices.domain.ContractorFromGusDto;
import com.crud.invoices.domain.Seller;
import com.crud.invoices.mapper.ContractorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.IUslugaBIRzewnPubl;

import java.util.Map;
import java.util.Optional;

@Service
public class BirService {

    @Autowired
    BirClient birClient;
    @Autowired
    ContractorMapper contractorMapper;

    public ContractorFromGusDto getContractorFromGus(final String nip) throws Exception {
        IUslugaBIRzewnPubl port = birClient.prepareApi();
        RegonType regonType = new RegonType();
        //regonType.setNip("5741739416");
        regonType.setNip(nip);
        ReportClient reportClient = new ReportClient(birClient, regonType);
        String basic = reportClient.getReport(regonType);
        ReportParser reportParser = new ReportParser();
        Map<String, String> map = reportParser.parseReport(basic);
        ContractorFromGusDto contractorFromGusDto = contractorMapper.mapToContractorFromGusDto(map);
        return Optional.ofNullable(contractorFromGusDto)
                .orElseThrow(() -> new RuntimeException("Contractor in service with such VAT identification number doesn't exist"));

    }

    public void saveSeller(Seller seller) {
    }
}

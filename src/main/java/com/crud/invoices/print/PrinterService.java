package com.crud.invoices.print;

import com.crud.invoices.domain.Invoice;
import com.crud.invoices.domain.Seller;
import com.crud.invoices.service.SellerService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PrinterService {
    @Autowired
    SellerService sellerService;

    public Object generatePDF(Long currentSellerId, Invoice invoice, String filePath) {

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);

            float [] columnWidths = {200F};
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("Invoice No.: " + invoice.getNumber())))
                    .addCell(new Cell().add(new Paragraph("Date of issue: " + invoice.getDateOfInvoice()+ " Date of sale: " + invoice.getDateOfSale())))
                    .addCell(new Cell().add(new Paragraph("Method of payment: " + invoice.getMethodOfPayment()+ " Period of payment: " + invoice.getPeriodOfPayment())));
            table.setMarginLeft(360F);

            Optional<Seller>seller = sellerService.getSeller(currentSellerId);
            String sellerName  = seller.get().getName();
            String sellerAddressCountry = seller.get().getAddress().getCountry();
            StringBuilder sb = new StringBuilder();
            String sellerAddressCity = seller.get().getAddress().getCity();
            String sellerAddressStreet = seller.get().getAddress().getStreet();
            String sellerAddressStreetNumber = seller.get().getAddress().getStreetNumber();
            String sellerVatIN = seller.get().getVatIdentificationNumber();
            sb.append(sellerAddressCity + " ");
            sb.append(sellerAddressStreet + " " + sellerAddressStreetNumber + "\n");
            sb.append("NIP: "+ sellerVatIN);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("\n" + "Nabywca: " +invoice.getContractor().getName());
            // Creating Paragraphs
            Paragraph paragraph1 = new Paragraph("Seller" +"\n"+ sellerName).setMultipliedLeading(2.2F)
                    .add("Contractor " + invoice.getContractor().getName());
            Paragraph paragraph2 = new Paragraph(sellerAddressCountry);
            Paragraph paragraph3 = new Paragraph(String.valueOf(sb));
            Paragraph paragraph4 = new Paragraph(String.valueOf(sb2));
            //table.setMarginBottom(80F);

            // Adding cells to the table
            // Creating a table
            float [] pointColumnWidths = {20F, 150F, 30F, 30F,60F,60F, 80F, 80F};
            Table table2 = new Table(pointColumnWidths);


            // Adding cells to the table
            table2
                    .addCell(new Cell().add(new Paragraph("No.")))
                    .addCell(new Cell().add(new Paragraph("Description")))
                    .addCell(new Cell().add(new Paragraph("Unit")))
                    .addCell(new Cell().add(new Paragraph("Qty")))
                    .addCell(new Cell().add(new Paragraph("net price")))
                    .addCell(new Cell().add(new Paragraph("Vat Rate")))
                    .addCell(new Cell().add(new Paragraph("Net Worth")))
                    .addCell(new Cell().add(new Paragraph("Gross Value")));

            Stream.iterate(0,i-> i + 1 )
                            .limit(invoice.getItems().size())
                    .forEach((i)-> {
                        table2.addCell(new Cell().add(new Paragraph(String.valueOf(i+1))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getProduct()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getUnit()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getAmount()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getNetWorth()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getVatRate()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getNetWorth()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getGrossValue()))));
            });
            document.add(table);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);

            document.add(table2);
            // Zamknięcie dokumentu
            document.close();
            System.out.println("PDF generated successfully.");
            System.setProperty("java.awt.headless", "false");
            openInvoice(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openInvoice(String filePath) {
        try {
            // Otwórz plik PDF przy użyciu domyślnego programu do przeglądania PDF
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("Plik PDF nie istnieje.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

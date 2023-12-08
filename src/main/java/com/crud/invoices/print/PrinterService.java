package com.crud.invoices.print;

import com.crud.invoices.domain.Invoice;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class PrinterService {


    public void generatePDF(Invoice invoice, String filePath) {

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            //PdfWriter.getInstance(document, new FileOutputStream(filePath));
            //document.open();
            // Creating a table
            float [] pointColumnWidths = {20F, 150F, 30F, 30F,60F,60F, 80F, 80F};
            Table table = new Table(pointColumnWidths);

            // Adding cells to the table
            table
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
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(i+1))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getProduct()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getUnit()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getAmount()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getNetWorth()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getVatRate()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getNetWorth()))))
                        .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().get(i).getGrossValue()))));
            });

          /* table
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item -> item.getProduct()).findFirst()))))
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item -> item.getUnit())))))
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item ->item.getAmount())))))
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item ->item.getNetWorth())))))
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item ->item.getVatRate())))))
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item ->item.getNetWorth())))))
                    .addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getItems().stream().filter(item -> item.getNumber()==1).map(item ->item.getGrossValue())))))
            */
            // Adding Table to document
            document.add(table);
            //document.add(new Paragraph(invoice));

            // Zamknięcie dokumentu
            document.close();
            System.out.println("PDF generated successfully.");
            System.setProperty("java.awt.headless", "false");
            openInvoice(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

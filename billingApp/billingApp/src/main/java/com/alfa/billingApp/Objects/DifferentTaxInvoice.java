package com.alfa.billingApp.Objects;

import com.alfa.billingApp.DAO.ProductDao;
import com.alfa.billingApp.DTO.InvoiceRequest;
import com.alfa.billingApp.DTO.ProductDetails;
import com.alfa.billingApp.entity.Invoice;
import com.alfa.billingApp.entity.Product;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Component("DifferentTaxInvoice")
public class DifferentTaxInvoice implements InvoiceInter {
    @Autowired
    ProductDao productDao;
    @Override
    public String generateInvoice(InvoiceRequest invoice, Invoice generatedInvoice) throws IOException {
        ClassPathResource template = new ClassPathResource("templates/Invoice_template.html");
        String html = new String(template.getInputStream().readAllBytes());

        String gstNo=invoice.getGstNo();
        String poNo=invoice.getPoNumber();

        StringBuilder header = new StringBuilder();
        header.append("<tr>")
                .append("<th>").append("Part No").append("</th>")
                .append("<th>").append("Product Name").append("</th>")
                .append("<th>").append("HSN").append("</th>")
                .append("<th>").append("Quantity").append("</th>")
                .append("<th>").append("Unit").append("</th>")
                .append("<th>").append("Rate").append("</th>")
                .append("<th>").append("Tax").append("</th>")
                .append("<th>").append("Amount").append("</th>")
                .append("</tr>");
        StringBuilder productDetails = new StringBuilder();
        double total=0.0;

        HashMap<Double,Double> taxMap=new HashMap<>();
        //setting user gn value for price product, has to get from db in case user giving stale values
        for (ProductDetails prdt:invoice.getPrdts()) {
            int hsn=prdt.getHsn();

            Product productFromDB=productDao.getProductByHsn(hsn,gstNo);
            double rate = productFromDB.getPrices().get(0).getRate();
            double qty=prdt.getQtys();
            double tax = productFromDB.getProductTax();
            double amount=rate*qty;

            productDetails.append("<tr>")
                    .append("<td>").append(prdt.getPartNo()).append("</td>")
                    .append("<td>").append(productFromDB.getProductName()).append("</td>")
                    .append("<td>").append(productFromDB.getHsn()).append("</td>")
                    .append("<td>").append(qty).append("</td>")
                    .append("<td>").append(productFromDB.getProductQty()).append("</td>")
                    .append("<td>").append(rate).append("</td>")
                    .append("<td>").append(tax+"%").append("</td>")
                    .append("<td>").append(String.format("%.2f",amount)).append("</td>")
                    .append("</tr>");
            total+= amount;
            double currTax=(tax/100)*amount;
            taxMap.put(tax,taxMap.getOrDefault(tax,0.0)+currTax);
        }

        StringBuilder taxItems = new StringBuilder();
        double grandTotal =total;
        for(Double taxKey:taxMap.keySet()){
            taxItems.append("<tr>")
                    .append("<td colspan=\"6\">").append("CGST ").append(taxKey+"%").append("</td>")
                    .append("<td >").append(String.format("%.2f",taxMap.get(taxKey))).append("</td>")
                    .append("</tr>")
                    .append("<tr>")
                    .append("<td colspan=\"6\">").append("SGST ").append(taxKey+"%").append("</td>")
                    .append("<td >").append(String.format("%.2f",taxMap.get(taxKey))).append("</td>")
                    .append("</tr>");
            grandTotal+=taxMap.get(taxKey)*2;
        }
        long invoiceNo = generatedInvoice.getInvoiceNo();
        grandTotal = Double.valueOf(String.format("%.2f",grandTotal));
        html = html.replace("${billNo}", invoiceNo+"")
                .replace("${billDate}",invoice.getInvoiceDate()+"")
                .replace("${poNo}", poNo)
                .replace("${poDate}", invoice.getPoDate()+"")
                .replace("${buyerGstin}",gstNo)
                .replace("${buyerName}",generatedInvoice.getCompany().getCompanyName()
                        +"/n"+generatedInvoice.getCompany().getAddr())
                .replace("${taxItems}",taxItems)
                .replace("${total}",String.format("%.2f",total)+"")
                .replace("${grandTotal}",grandTotal+"")
                .replace("${itemHeaders}",header)
                .replace("${itemRows}", productDetails);
        generatedInvoice.setAmount(Double.parseDouble(String.format("%.2f",total)));
//
 return html;
        }
}

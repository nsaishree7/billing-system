//package com.alfa.billingApp.Objects;
//
//import com.alfa.billingApp.DAO.ProductDao;
//import com.alfa.billingApp.DTO.InvoiceRequest;
//import com.alfa.billingApp.DTO.ProductDetails;
//import com.alfa.billingApp.entity.Invoice;
//import com.alfa.billingApp.entity.Product;
//import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//
//public class SeperateTaxInvoice implements InvoiceInter{
//
//    @Autowired
//    ProductDao productDao;
//
//    @Override
//    public String[] generateInvoice(InvoiceRequest invoice, Invoice generatedInvoice) throws IOException {
//        ClassPathResource template = new ClassPathResource("templates/Invoice_template.html");
//        String html = new String(template.getInputStream().readAllBytes());
//
//        StringBuilder header = new StringBuilder();
//        header.append("<tr>")
//                .append("<th>").append("Part No").append("</th>")
//                .append("<th>").append("Product Name").append("</th>")
//                .append("<th>").append("HSN").append("</th>")
//                .append("<th>").append("Quantity").append("</th>")
//                .append("<th>").append("Unit").append("</th>")
//                .append("<th>").append("Rate").append("</th>")
//                .append("<th>").append("Amount").append("</th>")
//                .append("</tr>");
//
//        String gstNo=generatedInvoice.getCompany().getGstNo();
//        String poNo=generatedInvoice.getCompany().getPurchaseOrderNumbers().get(0).getPurchaseOrderNumber();
//
//        HashMap<Double,StringBuilder> map=new HashMap<>();
//        HashMap<Double,Double> amountMap=new HashMap<>();
//        StringBuilder productDetails = new StringBuilder();
//
//
//
//        //setting user gn value for price product, has to get from db in case user giving stale values
//        for (ProductDetails prdt:invoice.getPrdts()) {
//
//            int hsn=prdt.getHsn();
//            Product productFromDB=productDao.getProductByHsn(hsn,gstNo);
//            double rate = productFromDB.getPrices().get(0).getRate();
//            double qty=prdt.getQtys();
//            double tax = productFromDB.getProductTax();
//            double amount=rate*qty;
//            if(!map.containsKey(tax)){
//                map.put(tax,new StringBuilder());
//                amountMap.put(tax,0.0);
//            }
//            map.get(tax).append("<tr>")
//                    .append("<td>").append(prdt.getPartNo()).append("</td>")
//                    .append("<td>").append(productFromDB.getProductName()).append("</td>")
//                    .append("<td>").append(prdt.getHsn()).append("</td>")
//                    .append("<td>").append(qty).append("</td>")
//                    .append("<td>").append(productFromDB.getProductQty()).append("</td>")
//                    .append("<td>").append(rate).append("</td>")
//                    .append("<td>").append(amount).append("</td>")
//                    .append("</tr>");
//            amountMap.put(tax,amountMap.get(tax)+amount);
//        }
//
//        html = html.replace("${billNo}", invoiceNo+"")
//                .replace("${billDate}",invoice.getInvoiceDate()+"")
//                .replace("${poNo}", poNo)
//                .replace("${poDate}", invoice.getPoDate()+"")
//                .replace("${buyerGstin}",gstNo)
//                .replace("${buyerName}",generatedInvoice.getCompany().getCompanyName()
//                        +"/n"+generatedInvoice.getCompany().getAddr())
//                .replace("${total}",total+"")
//                .replace("${cgst}",invoice.getPrdts().get(0).getProductTax()+"%")
//                .replace("${cgst}",invoice.getPrdts().get(0).getProductTax()+"%")
//                .replace("${itemRows}", productDetails);
//        //
////        // Create folders if not exist
//        File dir = new File(pdfFolder);
//        if (!dir.exists()) dir.mkdirs();
//
//        SimpleDateFormat date=new SimpleDateFormat();
//        String pdfFileName = invoiceNo+ ".pdf";
//        File pdfFile = new File(dir, pdfFileName);
////
//        try (OutputStream os = new FileOutputStream(pdfFile)) {
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.withHtmlContent(html, null);
//            builder.toStream(os);
//            builder.run();
//        }
//    }
//}

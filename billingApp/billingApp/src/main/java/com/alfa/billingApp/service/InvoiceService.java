package com.alfa.billingApp.service;

import com.alfa.billingApp.DAO.CompanyDao;
import com.alfa.billingApp.DAO.InvoiceDAO;
import com.alfa.billingApp.DAO.PoNumberDao;
import com.alfa.billingApp.DAO.ProductDao;
import com.alfa.billingApp.DTO.InvoiceRequest;
import com.alfa.billingApp.DTO.MinInvoiceRes;
import com.alfa.billingApp.Objects.InvoiceInter;
import com.alfa.billingApp.entity.*;
import com.alfa.billingApp.service.helperService.ProductHelperService;
import com.alfa.billingApp.utils.CompanyHelperService;
import com.alfa.billingApp.utils.FileTracker;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    InvoiceDAO invoiceDAO;

    @Autowired
    CompanyDao companyDao;
    @Autowired
    PoNumberDao poNumberDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductHelperService invoiceHelperService;

    @Value("${pdf.storage.path:generated-pdfs}")
    String pdfFolder;

    @Autowired
    @Qualifier("DifferentTaxInvoice")
    InvoiceInter invoiceGenerator;

    @Autowired
    FileTracker fileTracker;

    @Autowired
    CompanyHelperService daoService;
    @Transactional(rollbackFor = Exception.class)
    public File generateInvoice(InvoiceRequest invoice) throws IOException {

        //save invoice and get invoice no
        Invoice generatedInvoice=invoiceDAO.saveGeneratedInvoice(invoice);
        long invoiceNo=generatedInvoice.getInvoiceNo();

        String html = invoiceGenerator.generateInvoice(invoice,generatedInvoice);

        File dir = new File(pdfFolder);
        if (!dir.exists()) dir.mkdirs();

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        String pdfFileName = invoiceNo+"-"+formatter.format(invoice.getInvoiceDate())+ ".pdf";
        File pdfFile = new File(dir, pdfFileName);

        fileTracker.setFile(pdfFile);
        try (OutputStream os = new FileOutputStream(pdfFile)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
        }

//        if(true)
//        throw new IOException();
        //update path file to db
        invoiceDAO.updatePath(generatedInvoice,pdfFile.getAbsolutePath());

        //1.db save invoice (no path)  2.file  3.update path (invoice no generator gets rollback if any error)

        //file creation error (roll back saved datat in db) -- done aop-------------
        //1 error halt done --------------

        //indexing on invoice no for faster
        //sharding based on year
        return pdfFile;
    }


    public List<MinInvoiceRes> getAllNotPaid() {
        List<Invoice> inv=invoiceDAO.getAllNotPaid();
        List<MinInvoiceRes> res=new ArrayList<>();
        for(Invoice currInvoice:inv){
            res.add(invoiceHelperService.toMinInvoice(currInvoice))  ;
        }

        return res;
    }

    public List<MinInvoiceRes> getAll(String sortBy, int offset, int limit, String sortOrder) {
        Pageable page=daoService.getPage(sortBy,offset,limit,sortOrder);
        List<Invoice> inv=invoiceDAO.getAll(page);
        List<MinInvoiceRes> res=new ArrayList<>();
        for(Invoice currInvoice:inv){
            res.add(invoiceHelperService.toMinInvoice(currInvoice))  ;
        }
        return res;
    }

    public void updatePaid(long invoiceNo, boolean paidStatus) {
        invoiceDAO.updatPaidStatus(invoiceNo,paidStatus);
    }
}

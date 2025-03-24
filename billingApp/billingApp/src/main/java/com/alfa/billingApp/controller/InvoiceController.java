package com.alfa.billingApp.controller;

import com.alfa.billingApp.DTO.CompanyResponse;
import com.alfa.billingApp.DTO.InvoiceRequest;
import com.alfa.billingApp.DTO.MinInvoiceRes;
import com.alfa.billingApp.service.InvoiceService;
import com.alfa.billingApp.service.helperService.ProductHelperService;
import com.alfa.billingApp.utils.DefaultValues;
import com.alfa.billingApp.utils.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController(Url.Invoice)
@Validated
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    ProductHelperService helperService;
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest invoiceReq) throws IOException {
//        invoiceService.generateInvoice(helperService.toInvoiceDAO(invoice));
        File file=invoiceService.generateInvoice(invoiceReq);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        //how should browser process it
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());
        //exposing the headers so that frontend can see it in axios headers
headers.setAccessControlExposeHeaders(List.of("Content-Disposition"));
        return ResponseEntity.ok()
                .headers(headers)
                .body(Files.readAllBytes(file.toPath()));
    }


    @GetMapping("/notpaid")
    public ResponseEntity<List<MinInvoiceRes> > getAllNotPaid(){
            List<MinInvoiceRes> res=invoiceService.getAllNotPaid();
        return  new ResponseEntity<List<MinInvoiceRes> >(res, HttpStatus.OK);
    }

    @GetMapping(Url.Invoices)
    public  ResponseEntity<List<MinInvoiceRes>> getAll(@RequestParam(name="sortby",defaultValue = DefaultValues.DEFAULT_SORT_ORDER_INVOICE)String sortBy,
                                                       @RequestParam(defaultValue = DefaultValues.DEFAULT_OFFSET)int offset,
                                                       @RequestParam(defaultValue = DefaultValues.DEFAULT_LIMIT)int limit,
                                                       @RequestParam(defaultValue = "desc")String sortOrder){
        List<MinInvoiceRes> res=invoiceService.getAll(sortBy,offset,limit,sortOrder);
        return  new ResponseEntity<List<MinInvoiceRes> >(res, HttpStatus.OK);

    }

    @PutMapping(Url.UPDATE_PAID)
    public void updatePaid(@RequestParam long invoiceNo, @RequestParam boolean paidStatus){
        invoiceService.updatePaid(invoiceNo,paidStatus);
    }


}

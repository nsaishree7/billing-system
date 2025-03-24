package com.alfa.billingApp.DAO;

import com.alfa.billingApp.DTO.InvoiceRequest;
import com.alfa.billingApp.DTO.ProductDetails;
import com.alfa.billingApp.Exception.ResourceNotFoundException;
import com.alfa.billingApp.entity.*;
import com.alfa.billingApp.entity.JPAInterface.*;
import com.alfa.billingApp.service.helperService.ProductHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDAO {

    @Autowired
    InvoiceJPA invoiceJPA;

    @Autowired
    PurchaseOrderNumberJPA purchaseOrderNumberRepo;

    @Autowired
    CompanyJPA companyJPA;

    @Autowired
    ProductJpa productJpa;

    @Autowired
    InvoiceProductJpa invoiceProductJpa;


//    public Invoice saveGeneratedInvoice(InvoiceRequest invReq){
//        Invoice invoice = toInvoice(inv);
//        return invoiceJPA.save(invoice);
//    }

    public Invoice saveGeneratedInvoice(InvoiceRequest invReq){
        Invoice inv=new Invoice();
        String pono=invReq.getPoNumber();
        Company comp= companyJPA.findById(invReq.getGstNo())
                .orElseThrow(() -> new ResourceNotFoundException(invReq.getGstNo(),"gstNo  " ));
        PurchaseOrderNumber po = purchaseOrderNumberRepo.findById(pono)
                .orElseThrow(() -> new ResourceNotFoundException(pono,"PO " ));

        inv.setPo(po);
        inv.setCompany(comp);
        inv.setInvoiceDate(invReq.getInvoiceDate());

        Invoice savedInvoice = invoiceJPA.save(inv);//along with inv no

        //composite pk with mapsId doesn't automatically acquire value from product/invoice
        //set explicitly

        for(ProductDetails prdt:invReq.getPrdts()){
            InvoiceProduct invp=new InvoiceProduct();
            invp.setPk(new InvoiceProductPk(prdt.getHsn(), savedInvoice.getInvoiceNo()));
            invp.setQty(prdt.getQtys());
            Product product = productJpa.findById(prdt.getHsn())
                    .orElseThrow(() -> new ResourceNotFoundException(prdt.getHsn()+"","Product  " ));
            invp.setPrdt(product);
            savedInvoice.addInvoiceProduct(invp);
        }

        return invoiceJPA.save(savedInvoice);

    }

    public void updatePath(Invoice generatedInvoice, String absolutePath) {
        generatedInvoice.setFilePath(absolutePath);
        invoiceJPA.save(generatedInvoice);
    }

    public List<Invoice> getAllNotPaid() {
        return invoiceJPA.findAllWhoNotPaid();

    }

    public List<Invoice> getAll(Pageable page) {
        return invoiceJPA.getAll(page);
    }

    public void updatPaidStatus(long invoiceNo,boolean paidStatus) {
        Invoice inv = invoiceJPA.findById(invoiceNo).get();
        inv.setPaidStatus(paidStatus);
        invoiceJPA.save(inv);
    }
}

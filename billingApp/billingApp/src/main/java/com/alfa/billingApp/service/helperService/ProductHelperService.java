package com.alfa.billingApp.service.helperService;

import com.alfa.billingApp.DTO.InvoiceRequest;
import com.alfa.billingApp.DTO.MinInvoiceRes;
import com.alfa.billingApp.DTO.MinimalProductDTO;
import com.alfa.billingApp.DTO.ProductDetails;
import com.alfa.billingApp.Exception.MultipleResourceFoundException;
import com.alfa.billingApp.Exception.ResourceNotFoundException;
import com.alfa.billingApp.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductHelperService {

    public List<MinimalProductDTO> toListMinimalProductDto(List<Product> products) {
        List<MinimalProductDTO> minimalProductDTOs = new ArrayList<>();
        for (Product product : products) {
            MinimalProductDTO minimalProductDTO = new MinimalProductDTO();
            minimalProductDTO.setProductQty(product.getProductQty());
            minimalProductDTO.setProductName(product.getProductName());
            minimalProductDTO.setProductTax(product.getProductTax());
            minimalProductDTO.setHsn(product.getHsn());
            if(product.getPrices().size()>1)throw new MultipleResourceFoundException("Multiple prices found "+product.getPrices()+" for product"+product);
            if(product.getPrices().isEmpty())continue;
            minimalProductDTO.setPartNo(product.getPrices().get(0).getPartNo());
            minimalProductDTO.setRate(product.getPrices().get(0).getRate());
            minimalProductDTOs.add(minimalProductDTO);
        }
        return minimalProductDTOs;
    }
    
//    MinimalProductDTO t

    public Product toProduct(ProductDetails pd) {
        Product p=new Product();
        p.setProductName(pd.getProductName());
        p.setProductTax(pd.getProductTax());
        p.setProductQty(pd.getProductQty());
        p.setHsn(pd.getHsn());
       return p;
    }

    public Price toPrice(ProductDetails pd,String gstNo) {
        Price p=new Price();
        p.setPartNo(pd.getPartNo());
        p.setRate(pd.getRate());
        p.setId(new PricePk(pd.getHsn(),gstNo));
        return  p;
    }


    public MinInvoiceRes toMinInvoice(Invoice currInvoice) {
        MinInvoiceRes res=new MinInvoiceRes();
        res.setInvoiceNo(currInvoice.getInvoiceNo());
        res.setInvoiceDate(currInvoice.getInvoiceDate());
        res.setInvoiceAmt(currInvoice.getAmount());
        res.setCompanyGst(currInvoice.getCompany().getGstNo());
        res.setCompanyName(currInvoice.getCompany().getCompanyName());
        res.setPaidStatus(currInvoice.isPaidStatus());
        return res;
    }
}

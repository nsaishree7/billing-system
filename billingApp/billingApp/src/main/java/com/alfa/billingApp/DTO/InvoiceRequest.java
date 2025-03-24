package com.alfa.billingApp.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceRequest {

    @NotNull
    String gstNo;
    @NotNull
    String poNumber;
    @NotNull
    Date poDate;

    List<ProductDetails> prdts=new ArrayList<>();
    @NotNull
    Date invoiceDate;


}

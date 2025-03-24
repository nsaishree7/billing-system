package com.alfa.billingApp.DTO;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceResList {
    List<MinInvoiceRes> invoices;
    int totalRecords;
}

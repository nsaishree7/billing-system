package com.alfa.billingApp.Objects;

import com.alfa.billingApp.DTO.InvoiceRequest;
import com.alfa.billingApp.entity.Invoice;

import java.io.IOException;

public interface InvoiceInter {
    public String generateInvoice(InvoiceRequest invoiceRequest, Invoice inv) throws IOException;
}

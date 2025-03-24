package com.alfa.billingApp.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MinInvoiceRes {
    private long invoiceNo;
    private Date invoiceDate;
    private double invoiceAmt;

    private String companyName;
    private String companyGst;
    private boolean paidStatus;
}

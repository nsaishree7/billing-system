package com.alfa.billingApp.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MinimalProductDTO {

    private int hsn;
    private String productName;
    private double productTax;
    private String productQty;

    private int partNo;
    private double rate;
}

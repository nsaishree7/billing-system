package com.alfa.billingApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductDetails {
    @JsonProperty("productHsn")
    private int hsn;

    private String productName;
    private double productTax;
    private String productQty;

    private int partNo;

    @JsonProperty("productRate")
    private double rate;
    @JsonProperty("productNos")
    private int qtys;
    private int amount;

}

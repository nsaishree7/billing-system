package com.alfa.billingApp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceProductPk implements Serializable {

    @Column
    int hsn;

    @Column
    long invoiceNo;

    public InvoiceProductPk(int hsn){
        this.hsn=hsn;
    }
}

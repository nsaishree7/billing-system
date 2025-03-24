package com.alfa.billingApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PricePk implements Serializable {

    @Column(name="gstNo")
    private String companyGst;

    @Column(name="hsn")
    private int hsn;

    public PricePk(int hsn, String companyGst) {
        this.hsn = hsn;
        this.companyGst = companyGst;
    }
}

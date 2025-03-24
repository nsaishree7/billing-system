package com.alfa.billingApp.entity;

import com.alfa.billingApp.validation.GstNo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="company")
@NoArgsConstructor
public class Company {

    @Id
    @GstNo
    private String gstNo;

    @Column(name="companyName")
    private String companyName;

    @Column(name="addr")
    private String addr;

    //without mapped by will create a seperate table to map company to pos
    //mapping done by company
    @OneToMany(mappedBy="company")
    private List<PurchaseOrderNumber> purchaseOrderNumbers;

    @ManyToMany(mappedBy = "companies")
    private List<Product> products;

    @OneToMany(mappedBy = "company")
    private List<Invoice> invoices;


    public Company(String gstNo, String companyName, String addr) {
        this.gstNo = gstNo;
        this.companyName = companyName;
        this.addr = addr;
    }


    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setPurchaseOrderNumbers(List<PurchaseOrderNumber> purchaseOrderNumbers) {
        this.purchaseOrderNumbers = purchaseOrderNumbers;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getGstNo() {
        return gstNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddr() {
        return addr;
    }

    public List<PurchaseOrderNumber> getPurchaseOrderNumbers() {
        return purchaseOrderNumbers;
    }

    public List<Product> getProducts() {
        return products;
    }
}


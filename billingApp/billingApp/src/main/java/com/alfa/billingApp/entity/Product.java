package com.alfa.billingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @Column(name="hsn")
    private int hsn;

    @Column(name="productName")
    private String productName;

    @Column(name="productTax")
    private double productTax;

    @Column(name="productQty")
    private String productQty;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="productCompany",joinColumns = @JoinColumn(name="productHsn"),inverseJoinColumns = @JoinColumn(name="companyGst"))
    private List<Company> companies = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Price> prices = new ArrayList<>();

//    @ManyToMany(mappedBy = "prdt")
//    private List<Invoice> invoices;

    @OneToMany(mappedBy = "prdt")
    private List<InvoiceProduct> invoiceProducts;
}

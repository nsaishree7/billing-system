package com.alfa.billingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InvoiceProduct {
    @EmbeddedId
    InvoiceProductPk pk;

    @Column
    double qty;

    @ManyToOne
    @MapsId("hsn")  //maps the composite key as a foriegn key
    @JoinColumn(name = "hsn")
    Product prdt;

    @ManyToOne
    @MapsId("invoiceNo")  //maps the composite key as a foriegn key
//    @JoinColumn(name = "invoiceNo")
    Invoice inv;
}

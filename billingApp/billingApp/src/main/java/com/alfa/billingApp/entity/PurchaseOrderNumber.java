package com.alfa.billingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchaseOrderNumber")
@Data
public class PurchaseOrderNumber {

    @Id
    private String purchaseOrderNumber;

    @Column
    private Date purchaseOrderDate;

    @ManyToOne
    @JoinColumn(name="gstNo")
    private Company company;

    @OneToMany(mappedBy = "po")
    private List<Invoice> invoices;
}

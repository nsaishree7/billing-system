package com.alfa.billingApp.entity;

import com.alfa.billingApp.utils.DbSequenceGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Invoice {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_sequence")
    @SequenceGenerator(name = "invoice_sequence", sequenceName = "invoice_sequence", allocationSize = 1)
    private long invoiceNo;

//    @Column
//    private long invoiceNo;

    @Column
    double amount;

    @Column
    private Date invoiceDate;

    @ManyToOne
    @JoinColumn(name="company_gst")
    private Company company;
    @Column
    private String filePath;

    @Column
    private boolean paidStatus;

    @ManyToOne
    @JoinColumn(name="invoice_po")
    private PurchaseOrderNumber po ;

    @OneToMany(mappedBy = "inv",cascade = CascadeType.ALL, orphanRemoval = true)
    List<InvoiceProduct> invoiceProducts = new ArrayList<>();

    public void addInvoiceProduct(InvoiceProduct product) {
        invoiceProducts.add(product);
        product.setInv(this); // Ensure bidirectional mapping
    }

}

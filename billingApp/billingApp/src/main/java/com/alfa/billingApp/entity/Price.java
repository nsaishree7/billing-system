package com.alfa.billingApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "price")
public class Price {

    @EmbeddedId
   private PricePk id;

    @Column
    private double rate;

    @Column
    private int partNo;  // in price tagble since part no can change for diff company

    public Price(PricePk id, double rate, int partNo) {
        this.id = id;
        this.rate = rate;
        this.partNo = partNo;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("hsn")  //maps the composite key as a foriegn key
    @JoinColumn(name = "hsn")
    private Product product;

}


//"22AAAAA0000A1Z5"	6116	4156	100
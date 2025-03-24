package com.alfa.billingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class InvoiceIdGenerator {

    @Id
    private String entityName;
    private Long nextVal;
}

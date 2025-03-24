package com.alfa.billingApp.entity.JPAInterface;

import com.alfa.billingApp.entity.InvoiceProduct;
import com.alfa.billingApp.entity.InvoiceProductPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductJpa extends  JpaRepository<InvoiceProduct, InvoiceProductPk> {
}

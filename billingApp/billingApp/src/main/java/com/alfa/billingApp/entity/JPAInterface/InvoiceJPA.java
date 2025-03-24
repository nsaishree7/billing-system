package com.alfa.billingApp.entity.JPAInterface;

import com.alfa.billingApp.entity.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceJPA extends JpaRepository<Invoice,Long> {

    @Query("select i from Invoice i where i.paidStatus=false")
     List<Invoice> findAllWhoNotPaid();

    @Query("select i from Invoice i")
    List<Invoice> getAll( Pageable pageable);
}

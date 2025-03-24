package com.alfa.billingApp.entity.JPAInterface;

import com.alfa.billingApp.entity.PurchaseOrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderNumberJPA extends JpaRepository<PurchaseOrderNumber, String> {

    @Query("select p from PurchaseOrderNumber p where lower(p.company.gstNo) = lower(:gstNo)")
    List<PurchaseOrderNumber> getAllByGst(String gstNo);
}

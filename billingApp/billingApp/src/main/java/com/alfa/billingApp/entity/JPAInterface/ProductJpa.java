package com.alfa.billingApp.entity.JPAInterface;


import com.alfa.billingApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpa extends JpaRepository<Product, Integer> {

    @Query("select p from Product p join p.companies c where c.gstNo=:gstNo")
    List<Product> getAllByGstNo(@Param("gstNo") String gstNo);

    @Query("select p from Product p join p.companies c where c.gstNo=:gstNo AND p.hsn=:hsn")
    Product getProductByHsnGst(@Param("gstNo") String gstNo,@Param("hsn")int hsn);
}

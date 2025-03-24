package com.alfa.billingApp.entity.JPAInterface;

import com.alfa.billingApp.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyJPA extends JpaRepository<Company, String> {

    @Query("select c from Company c where upper(c.gstNo) like upper(CONCAT(:gstNo,'%'))")
    List<Company> searchByGstNo(@Param("gstNo") String searchParm, Pageable pageable);


    @Query("select c from Company c where upper(c.companyName) like upper(CONCAT(:cName,'%')) ")
    List<Company> searchByCompanyName(@Param("cName") String companyName, Pageable page);

    @Query("select c from Company c where c.gstNo=:gstNo")
    Company findByGst(String gstNo);

    @Query("select c from Company c where c.companyName=:companyName")
    Company getCompanyDetailsByCompanyName(@Param("companyName") String companyName);
}

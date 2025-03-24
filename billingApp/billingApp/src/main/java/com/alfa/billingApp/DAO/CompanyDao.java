package com.alfa.billingApp.DAO;

import com.alfa.billingApp.Exception.MultipleResourceFoundException;
import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.JPAInterface.CompanyJPA;
import com.alfa.billingApp.entity.PurchaseOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDao {

    @Autowired
    CompanyJPA companyRepo;
    public List<Company> searchByGstInDao(String gstNo, Pageable page) {
        return companyRepo.searchByGstNo(gstNo, page);
    }

    public List<Company> searchByCompanyName(String companyName, Pageable page) {
        return companyRepo.searchByCompanyName(companyName,page);
    }


    public void saveEntity(Company companyDAO) {
        if(companyRepo.existsById(companyDAO.getGstNo()))
            throw new MultipleResourceFoundException("Company of GST "+companyDAO.getGstNo()+" already exists.");
        companyRepo.save(companyDAO);
    }

    public Company getCompany(String companyName) {
       return companyRepo.getCompanyDetailsByCompanyName(companyName);
    }

    public boolean companyExist(String companyGst){
        return companyRepo.existsById(companyGst);
    }
}

package com.alfa.billingApp.service;

import com.alfa.billingApp.DAO.CompanyDao;
import com.alfa.billingApp.DAO.PoNumberDao;
import com.alfa.billingApp.DTO.ACompany;
import com.alfa.billingApp.DTO.CompanyResponse;
import com.alfa.billingApp.DTO.PoResponse;
import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.PurchaseOrderNumber;
import com.alfa.billingApp.utils.CompanyHelperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyHelperService daoService;
    @Autowired
    CompanyDao companyDao;

    @Autowired
    PoNumberDao poDao;

    public CompanyResponse findAllByGst(String gstNo, String sortBy, int offset, int limit, String sortOrder) {
        Pageable page = daoService.getPage( sortBy,  offset,  limit,  sortOrder);

        return daoService.toCompanyResponseDTO(companyDao.searchByGstInDao(gstNo,page));
    }

    public CompanyResponse getCompanyByCompanyName(String companyName, String sortBy, int offset, int limit, String sortOrder) {
        Pageable page = daoService.getPage( sortBy,  offset,  limit,  sortOrder);
        return daoService.toCompanyResponseDTO(companyDao.searchByCompanyName(companyName,page));
    }



    public void save(@Valid ACompany companyDetails) {
        companyDao.saveEntity(daoService.toCompanyDAO(companyDetails));
    }

    public ACompany getCompany(String companyName) {
       return daoService.toACompanyResponse(companyDao.getCompany(companyName));
    }
}

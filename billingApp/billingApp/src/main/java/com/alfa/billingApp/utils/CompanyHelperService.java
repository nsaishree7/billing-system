package com.alfa.billingApp.utils;

import com.alfa.billingApp.DTO.ACompany;
import com.alfa.billingApp.DTO.CompanyResponse;
import com.alfa.billingApp.DTO.Po;
import com.alfa.billingApp.DTO.PoResponse;
import com.alfa.billingApp.Exception.ResourceNotFoundException;
import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.PurchaseOrderNumber;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyHelperService {


    public Pageable getPage(String sortBy, int offset, int limit, String sortOrder) {
        Pageable page= PageRequest.of(offset,limit, Sort.by(sortBy));
        if(sortOrder.equals("desc")||sortOrder.equals("dsc"))
            return PageRequest.of(offset,limit, Sort.by(sortBy).descending());
        return page;
    }

    public CompanyResponse toCompanyResponseDTO(List<Company> companies) {
        CompanyResponse c = new CompanyResponse();
        for(Company company : companies) {
            ACompany aCompany = new ACompany();
            aCompany.setCompanyName(company.getCompanyName());
            aCompany.setCompanyAddress(company.getAddr());
            aCompany.setGstNo(company.getGstNo());
            c.getListOfCompanies().add(aCompany);
        }
        c.setTotalRecords(companies.size());
        return c;
    }


    public PoResponse toPoResponse(List<PurchaseOrderNumber> allPo) {
        PoResponse p = new PoResponse();
        for(PurchaseOrderNumber po : allPo) {
            p.getPoNumbers().add(new Po(po.getPurchaseOrderNumber(),po.getPurchaseOrderDate()));
        }
        return p;
    }

    public Company toCompanyDAO(@Valid ACompany companyDetails) {
        return new Company(companyDetails.getGstNo(),companyDetails.getCompanyName(),companyDetails.getCompanyAddress());
    }

    public ACompany toACompanyResponse(Company company){
        ACompany companyResponse =new ACompany();
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setCompanyAddress(company.getAddr());
        companyResponse.setGstNo(company.getGstNo());
        return  companyResponse;
    }

    public PurchaseOrderNumber toPoModel(Po po) {
        PurchaseOrderNumber modelPo=new PurchaseOrderNumber();
        modelPo.setPurchaseOrderDate(po.getPoDate());
        modelPo.setPurchaseOrderNumber(po.getPoNumber());
        return modelPo;
    }
}

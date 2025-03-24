package com.alfa.billingApp.controller;

import com.alfa.billingApp.DTO.ACompany;
import com.alfa.billingApp.DTO.CompanyResponse;
import com.alfa.billingApp.DTO.PoResponse;
import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.JPAInterface.PriceJpa;
import com.alfa.billingApp.entity.JPAInterface.ProductJpa;
import com.alfa.billingApp.entity.Price;
import com.alfa.billingApp.entity.PricePk;
import com.alfa.billingApp.entity.Product;
import com.alfa.billingApp.service.CompanyService;
import com.alfa.billingApp.utils.DefaultValues;
import com.alfa.billingApp.utils.Url;
import com.alfa.billingApp.validation.GstNo;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ProductJpa productJpa;

    @GetMapping(Url.searchByGst)
    public ResponseEntity<CompanyResponse> searchByGst(@RequestParam(name="gstno",defaultValue = "") String gstNo,
                                                       @RequestParam(name="sortby",defaultValue = DefaultValues.DEFAULT_SORT_ORDER)String sortBy,
                                                       @RequestParam(defaultValue = DefaultValues.DEFAULT_OFFSET)int offset,
                                                       @RequestParam(defaultValue = DefaultValues.DEFAULT_LIMIT)int limit,
                                                       @RequestParam(defaultValue = "desc")String sortOrder){

        CompanyResponse res= companyService.findAllByGst(gstNo,sortBy,offset,limit,sortOrder);
        return new ResponseEntity<CompanyResponse>(res, HttpStatus.OK);
    }


    @GetMapping(Url.searchByCompany)
    public ResponseEntity<CompanyResponse> searchByCompanyName(@RequestParam(name="companyname",defaultValue = "") String companyName,
                                                               @RequestParam(name="sortby",defaultValue = DefaultValues.DEFAULT_SORT_ORDER)String sortBy,
                                                               @RequestParam(defaultValue = DefaultValues.DEFAULT_OFFSET)int offset,
                                                               @RequestParam(defaultValue = DefaultValues.DEFAULT_LIMIT)int limit,
                                                               @RequestParam(defaultValue = "desc")String sortOrder)
    {
       CompanyResponse res= companyService.getCompanyByCompanyName(companyName,sortBy,offset,limit,sortOrder);
       return  new ResponseEntity<CompanyResponse>(res,HttpStatus.OK);
    }



    @PostMapping(Url.company)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompany(@RequestBody ACompany companyDetails){
        companyService.save(companyDetails);
    }

    @GetMapping(Url.company)
    public ResponseEntity<ACompany> getCompanyByCompanyName(@RequestParam(name="companyname") String companyName){
        ACompany cmpny=companyService.getCompany(companyName);
        return new ResponseEntity<ACompany>(cmpny,HttpStatus.OK);
    }

//    @Autowired
//    PriceJpa priceJpa;
//    @GetMapping("/save")
//    public void save(){
//        Product p=new Product();
//        List<Company> c= Arrays.asList(new Company("22AAAAA0000A1Z5","Carbondom","mig df,\n" + "Assam"),
//                new Company("36AAACH7409R1Z2","Sai book agency","new york,\n" +
//                        "london"));
//        p.setCompanies(c);
//        p.setHsn(6116);
//        p.setProductTax(2.5);
//        p.setProductName("new product");
//        p.setPrices(Arrays.asList(new Price(new PricePk(6116,"22AAAAA0000A1Z5"),100,4156)));
//
//        Price pr =new Price(new PricePk(6116,"22AAAAA0000A1Z5"),100,4156);
//        priceJpa.save(pr);
//
//
//        productJpa.save(p);
//    }



//    @PostMapping(Url.company)
//    @ResponseStatus(HttpStatus.OK)
//    public void addCompany()

}

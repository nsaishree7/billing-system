package com.alfa.billingApp.DAO;

import com.alfa.billingApp.Exception.ResourceNotFoundException;
import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.JPAInterface.CompanyJPA;
import com.alfa.billingApp.entity.JPAInterface.PurchaseOrderNumberJPA;
import com.alfa.billingApp.entity.PurchaseOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PoNumberDao {

    @Autowired
    PurchaseOrderNumberJPA poRepo;

    @Autowired
    CompanyJPA companyRepo;
    public List<PurchaseOrderNumber> getAllPo(String gstNo) {
        if(!companyRepo.existsById(gstNo))throw new ResourceNotFoundException(gstNo,"GST No");

        List<PurchaseOrderNumber> res= poRepo.getAllByGst(gstNo);
        if(res.isEmpty())throw new ResourceNotFoundException(null,"Po No");

        return res;
    }

    public void save(PurchaseOrderNumber poModel, String gstNo) {
        Company company = companyRepo.findByGst(gstNo);
        if(company==null)throw new ResourceNotFoundException(gstNo," gst no ");
        poModel.setCompany(company);
        poRepo.save(poModel);
    }

    public boolean poExists(String poNumber){
        return poRepo.existsById(poNumber);
    }
}

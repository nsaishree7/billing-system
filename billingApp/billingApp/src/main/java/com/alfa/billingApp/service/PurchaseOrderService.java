package com.alfa.billingApp.service;

import com.alfa.billingApp.DAO.PoNumberDao;
import com.alfa.billingApp.DTO.Po;
import com.alfa.billingApp.DTO.PoResponse;
import com.alfa.billingApp.entity.PurchaseOrderNumber;
import com.alfa.billingApp.utils.CompanyHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {

    @Autowired
    CompanyHelperService daoService;
    @Autowired
    PoNumberDao poDao;
    public PoResponse getAllPo(String gstNo) {
        return daoService.toPoResponse(poDao.getAllPo(gstNo));
    }

    public void addPo(String gstNo, Po po) {
        poDao.save(daoService.toPoModel(po),gstNo);

    }
}

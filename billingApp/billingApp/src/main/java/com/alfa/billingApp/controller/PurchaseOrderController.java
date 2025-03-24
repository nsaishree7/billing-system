package com.alfa.billingApp.controller;

import com.alfa.billingApp.DTO.Po;
import com.alfa.billingApp.DTO.PoResponse;
import com.alfa.billingApp.service.PurchaseOrderService;
import com.alfa.billingApp.utils.Url;
import com.alfa.billingApp.validation.GstNo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController(Url.purchaseOrder)
@Validated
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService poService;
    @GetMapping(Url.purchaseOrder)
    public ResponseEntity<PoResponse> getDetailsOfCompany(@RequestParam(name="gstno") @GstNo String gstNo){
        PoResponse res=poService.getAllPo(gstNo);
        return new ResponseEntity<PoResponse>(res, HttpStatus.OK);
    }

    @PostMapping(Url.purchaseOrder)
    @ResponseStatus(HttpStatus.CREATED)
    public void addPo(@RequestParam(name="gstno") @GstNo String gstNo,@RequestBody Po po){
        poService.addPo(gstNo,po);
    }
}

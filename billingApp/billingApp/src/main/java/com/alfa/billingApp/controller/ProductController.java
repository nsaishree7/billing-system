package com.alfa.billingApp.controller;

import com.alfa.billingApp.DTO.MinimalProductDTO;
import com.alfa.billingApp.DTO.ProductDetails;
import com.alfa.billingApp.service.ProductService;
import com.alfa.billingApp.utils.Url;
import com.alfa.billingApp.validation.GstNo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class ProductController {

    @Autowired
    ProductService productService;
    //get all partno

    //1.get all product name
    //get product details(even price rate partno) for product
    @GetMapping(Url.products)
    ResponseEntity<List<MinimalProductDTO>>  getALlProductsByGst(@RequestParam(name="gstNo") @GstNo String gstNo){
        List<MinimalProductDTO> res= productService.getAllProductsByGst(gstNo);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(Url.products)
    @ResponseStatus(HttpStatus.CREATED)
    void addProductWithGst(@RequestBody ProductDetails pd,@RequestParam(name="gstNo") @GstNo String gstNo){
        productService.addProduct(pd,gstNo);
    }

    @PutMapping(Url.products)
    @ResponseStatus(HttpStatus.OK)
    void updateProduct(@RequestBody ProductDetails pd,@RequestParam(name="gstNo") @GstNo String gstNo){
        productService.updateProduct(pd,gstNo);
    }


}

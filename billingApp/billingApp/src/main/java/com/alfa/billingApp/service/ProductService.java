package com.alfa.billingApp.service;

import com.alfa.billingApp.DAO.ProductDao;
import com.alfa.billingApp.DTO.MinimalProductDTO;
import com.alfa.billingApp.DTO.ProductDetails;
import com.alfa.billingApp.entity.Price;
import com.alfa.billingApp.entity.Product;
import com.alfa.billingApp.service.helperService.ProductHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductHelperService helperService;

    public List<MinimalProductDTO> getAllProductsByGst(String gstNo) {
        List<Product> products = productDao.getAllProductsByGst(gstNo);
        return helperService.toListMinimalProductDto(products);
    }

    public void addProduct(ProductDetails pd, String gstNo) {
        Price price=helperService.toPrice(pd,gstNo);
        productDao.addProduct(helperService.toProduct(pd),price,gstNo);
    }

    public void updateProduct(ProductDetails pd, String gstNo) {
        Price price=helperService.toPrice(pd,gstNo);
        productDao.updateProduct(helperService.toProduct(pd),price,gstNo);
    }
}

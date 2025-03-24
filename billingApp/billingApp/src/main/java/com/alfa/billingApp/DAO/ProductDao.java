package com.alfa.billingApp.DAO;

import com.alfa.billingApp.Exception.MultipleResourceFoundException;
import com.alfa.billingApp.Exception.ResourceNotFoundException;
import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.JPAInterface.CompanyJPA;
import com.alfa.billingApp.entity.JPAInterface.ProductJpa;
import com.alfa.billingApp.entity.Price;
import com.alfa.billingApp.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {

    @Autowired
    ProductJpa productJpa;

    @Autowired
    CompanyJPA companyJPA;

    public List<Product> getAllProductsByGst(String gstNo) {
        return productJpa.getAllByGstNo(gstNo);
    }

    public void addProduct(Product product, Price price, String gstNo) {
        Company company = companyJPA.findByGst(gstNo);
        if (company == null) throw new ResourceNotFoundException(gstNo, "GST Number");

        product.getCompanies().add(company);
        price.setProduct(product);
        Optional<?> productIndb=productJpa.findById(product.getHsn());
        if(productIndb.isPresent()) {
            Product prdt= (Product) productIndb.get();
            boolean priceExists = prdt.getPrices().stream().anyMatch((p) -> (price.getId().getHsn() == p.getId().getHsn()
                    && price.getId().getCompanyGst().equals(p.getId().getCompanyGst())));
            if (priceExists) throw new MultipleResourceFoundException("price " + price.getId() + " exists already");
        }
        product.getPrices().add(price);


        productJpa.save(product);

    }

    public void updateProduct(Product product, Price price, String gstNo) {
        Company company = companyJPA.findByGst(gstNo);
        if (company == null) throw new ResourceNotFoundException(gstNo, "GST Number");
        Optional<?> productIndb=productJpa.findById(product.getHsn());
        if(productIndb.isEmpty()) throw new ResourceNotFoundException(String.valueOf(product.getHsn()),"product hsn");
//        else {
//            Product prdt= (Product) productIndb.get();
//            boolean priceExists = prdt.getPrices().stream().anyMatch((p) -> (price.getId().getHsn() == p.getId().getHsn()
//                    && price.getId().getCompanyGst().equals(p.getId().getCompanyGst())));
//            if (!priceExists) throw new ResourceNotFoundException( String.valueOf(price.getId()) ," Price ");
//        }

        Product prdt= (Product) productIndb.get();
        prdt.setProductQty(product.getProductQty());
        prdt.setProductTax(product.getProductTax());
        prdt.setProductName(product.getProductName());
        prdt.getPrices().stream().forEach(eachPrice->{
            if(price.getId().getHsn() == eachPrice.getId().getHsn()
                    && price.getId().getCompanyGst().equals(eachPrice.getId().getCompanyGst())){
                eachPrice.setRate(price.getRate());
                eachPrice.setPartNo(price.getPartNo());
            }
        });

        productJpa.save(prdt);
    }

    public void updatePrice(Price eachPrice,Price price){

    }

    public Product getProductByHsn(int hsn, String gstNo){
        return productJpa.getProductByHsnGst(gstNo,hsn);
    }
}

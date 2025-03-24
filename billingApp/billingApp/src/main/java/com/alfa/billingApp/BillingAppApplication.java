package com.alfa.billingApp;

import com.alfa.billingApp.entity.Company;
import com.alfa.billingApp.entity.JPAInterface.ProductJpa;
import com.alfa.billingApp.entity.Price;
import com.alfa.billingApp.entity.PricePk;
import com.alfa.billingApp.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.alfa.billingApp")
@CrossOrigin(origins = "http://localhost:3000")
@EnableAspectJAutoProxy
public class BillingAppApplication {

	@Autowired
	static ProductJpa productJpa;
	public static void main(String[] args) {
		SpringApplication.run(BillingAppApplication.class, args);


	}

}

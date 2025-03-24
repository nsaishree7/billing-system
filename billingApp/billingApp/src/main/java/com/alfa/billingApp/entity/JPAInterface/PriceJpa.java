package com.alfa.billingApp.entity.JPAInterface;


import com.alfa.billingApp.entity.Price;
import com.alfa.billingApp.entity.PricePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceJpa extends JpaRepository<Price, PricePk> {
}

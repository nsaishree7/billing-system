package com.alfa.billingApp.DTO;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyResponse {
   private List<ACompany> listOfCompanies = new ArrayList<>();

   private int totalRecords;
}

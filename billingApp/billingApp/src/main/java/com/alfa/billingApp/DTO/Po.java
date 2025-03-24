package com.alfa.billingApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Po {
    private String poNumber;
    private Date poDate;
}

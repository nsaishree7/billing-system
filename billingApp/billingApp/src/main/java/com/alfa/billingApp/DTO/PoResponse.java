package com.alfa.billingApp.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PoResponse {
    private List<Po> poNumbers = new ArrayList<>();
}

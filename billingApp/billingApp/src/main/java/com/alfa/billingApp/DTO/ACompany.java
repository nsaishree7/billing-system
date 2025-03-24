package com.alfa.billingApp.DTO;

import com.alfa.billingApp.validation.GstNo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ACompany {
    @NotNull
    private String companyName;
    private String companyAddress;
    @GstNo
    private String gstNo;
}

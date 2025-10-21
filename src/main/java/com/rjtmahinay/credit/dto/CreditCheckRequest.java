package com.rjtmahinay.credit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCheckRequest {
    
    private String ssn;
    private String firstName;
    private String lastName;
    private BigDecimal requestedAmount;
    private String loanType;
    private Integer termMonths;
    private BigDecimal annualIncome;
    private String employmentStatus;
}

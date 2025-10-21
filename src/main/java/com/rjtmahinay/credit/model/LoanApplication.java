package com.rjtmahinay.credit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_applications")
public class LoanApplication {
    
    @Id
    private Long id;
    
    private String applicationId;
    private String ssn;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private BigDecimal requestedAmount;
    private String loanType; // PERSONAL, MORTGAGE, AUTO, BUSINESS
    private Integer termMonths;
    private BigDecimal annualIncome;
    private String employmentStatus; // EMPLOYED, SELF_EMPLOYED, UNEMPLOYED, RETIRED
    private String status; // PENDING, APPROVED, REJECTED, UNDER_REVIEW
    private String rejectionReason;
    private LocalDateTime applicationDate;
    private LocalDateTime processedDate;
    
    public enum LoanType {
        PERSONAL, MORTGAGE, AUTO, BUSINESS
    }
    
    public enum EmploymentStatus {
        EMPLOYED, SELF_EMPLOYED, UNEMPLOYED, RETIRED
    }
    
    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED, UNDER_REVIEW
    }
}

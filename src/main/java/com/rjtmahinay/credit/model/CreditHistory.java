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
@Table("credit_history")
public class CreditHistory {
    
    @Id
    private Long id;
    
    private String ssn;
    private String accountType; // CREDIT_CARD, LOAN, MORTGAGE, AUTO_LOAN
    private String creditorName;
    private BigDecimal originalAmount;
    private BigDecimal currentBalance;
    private BigDecimal creditLimit;
    private String paymentStatus; // CURRENT, LATE_30, LATE_60, LATE_90, CHARGED_OFF, COLLECTIONS
    private Integer daysLate;
    private LocalDateTime accountOpenDate;
    private LocalDateTime lastPaymentDate;
    private LocalDateTime reportedDate;
    private Boolean isActive;
    
    public enum AccountType {
        CREDIT_CARD, LOAN, MORTGAGE, AUTO_LOAN
    }
    
    public enum PaymentStatus {
        CURRENT, LATE_30, LATE_60, LATE_90, CHARGED_OFF, COLLECTIONS
    }
}

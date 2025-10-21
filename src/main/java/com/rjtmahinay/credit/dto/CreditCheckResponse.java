package com.rjtmahinay.credit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCheckResponse {
    
    private String ssn;
    private String firstName;
    private String lastName;
    private Integer creditScore;
    private String riskLevel;
    private Boolean isApproved;
    private String decision; // APPROVED, REJECTED, MANUAL_REVIEW
    private String rejectionReason;
    private BigDecimal approvedAmount;
    private BigDecimal recommendedInterestRate;
    private List<CreditHistorySummary> creditHistory;
    private LocalDateTime checkDate;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditHistorySummary {
        private String accountType;
        private String creditorName;
        private BigDecimal currentBalance;
        private String paymentStatus;
        private Integer daysLate;
    }
    
    public enum Decision {
        APPROVED, REJECTED, MANUAL_REVIEW
    }
}

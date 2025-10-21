package com.rjtmahinay.credit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("credit_scores")
public class CreditScore {
    
    @Id
    private Long id;
    
    private String ssn;
    private String firstName;
    private String lastName;
    private Integer score;
    private String riskLevel; // LOW, MEDIUM, HIGH
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    
    public enum RiskLevel {
        LOW, MEDIUM, HIGH
    }
}

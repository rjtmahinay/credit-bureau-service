package com.rjtmahinay.credit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update an existing credit history record")
public class UpdateCreditHistoryRequest {

    @Schema(description = "Account type (CREDIT_CARD, LOAN, MORTGAGE, AUTO_LOAN)", example = "CREDIT_CARD")
    private String accountType;

    @Schema(description = "Creditor name", example = "Chase Bank")
    private String creditorName;

    @Schema(description = "Original loan/credit amount", example = "5000.00")
    private BigDecimal originalAmount;

    @Schema(description = "Current balance", example = "1200.00")
    private BigDecimal currentBalance;

    @Schema(description = "Credit limit", example = "5000.00")
    private BigDecimal creditLimit;

    @Schema(description = "Payment status (CURRENT, LATE_30, LATE_60, LATE_90, CHARGED_OFF, COLLECTIONS)", example = "CURRENT")
    private String paymentStatus;

    @Schema(description = "Days late on payment", example = "0")
    private Integer daysLate;

    @Schema(description = "Account open date", example = "2020-01-01T00:00:00")
    private LocalDateTime accountOpenDate;

    @Schema(description = "Last payment date", example = "2024-10-15T00:00:00")
    private LocalDateTime lastPaymentDate;

    @Schema(description = "Is account active", example = "true")
    private Boolean isActive;
}

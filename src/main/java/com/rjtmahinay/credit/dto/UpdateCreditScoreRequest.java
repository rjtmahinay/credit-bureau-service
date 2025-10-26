package com.rjtmahinay.credit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update an existing credit score record")
public class UpdateCreditScoreRequest {

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Credit score value (300-850)", example = "720")
    private Integer score;

    @Schema(description = "Risk level (LOW, MEDIUM, HIGH)", example = "LOW")
    private String riskLevel;
}

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
@Schema(description = "Request to create a new credit score record")
public class CreateCreditScoreRequest {

    @Schema(description = "Social Security Number", example = "123-45-6789", required = true)
    private String ssn;

    @Schema(description = "First name", example = "John", required = true)
    private String firstName;

    @Schema(description = "Last name", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "Credit score value (300-850)", example = "720", required = true)
    private Integer score;

    @Schema(description = "Risk level (LOW, MEDIUM, HIGH)", example = "LOW")
    private String riskLevel;
}

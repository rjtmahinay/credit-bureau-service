package com.rjtmahinay.credit.controller;

import com.rjtmahinay.credit.dto.CreditCheckRequest;
import com.rjtmahinay.credit.dto.CreditCheckResponse;
import com.rjtmahinay.credit.model.CreditHistory;
import com.rjtmahinay.credit.model.CreditScore;
import com.rjtmahinay.credit.model.LoanApplication;
import com.rjtmahinay.credit.service.CreditBureauService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/credit")
@RequiredArgsConstructor
@Tag(name = "Credit Bureau", description = "Credit Bureau Service API for credit checks, scores, and loan applications")
public class CreditBureauController {
    
    private final CreditBureauService creditBureauService;
    
    @Operation(
        summary = "Perform Credit Check",
        description = "Performs a comprehensive credit check for a given SSN and returns credit assessment details"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Credit check completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreditCheckResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error during credit check")
    })
    @PostMapping("/check")
    public Mono<ResponseEntity<CreditCheckResponse>> performCreditCheck(
        @Parameter(description = "Credit check request containing SSN and other details", required = true)
        @RequestBody CreditCheckRequest request) {
        log.info("Received credit check request for SSN: {}", request.getSsn());
        
        return creditBureauService.performCreditCheck(request)
                .map(response -> ResponseEntity.ok(response))
                .onErrorResume(error -> {
                    log.error("Error performing credit check", error);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
    
    @Operation(
        summary = "Get Credit Score",
        description = "Retrieves the current credit score for a specific SSN"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Credit score found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreditScore.class))),
        @ApiResponse(responseCode = "404", description = "Credit score not found for the given SSN")
    })
    @GetMapping("/score/{ssn}")
    public Mono<ResponseEntity<CreditScore>> getCreditScore(
        @Parameter(description = "Social Security Number", required = true, example = "123-45-6789")
        @PathVariable String ssn) {
        log.info("Fetching credit score for SSN: {}", ssn);
        
        return creditBureauService.getCreditScoreBySSN(ssn)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Credit score not found for SSN: {}", ssn, error);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }
    
    @Operation(
        summary = "Get Credit History",
        description = "Retrieves the complete credit history for a specific SSN"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Credit history retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreditHistory.class)))
    })
    @GetMapping("/history/{ssn}")
    public Flux<CreditHistory> getCreditHistory(
        @Parameter(description = "Social Security Number", required = true, example = "123-45-6789")
        @PathVariable String ssn) {
        log.info("Fetching credit history for SSN: {}", ssn);
        return creditBureauService.getCreditHistoryBySSN(ssn);
    }
    
    @Operation(
        summary = "Submit Loan Application",
        description = "Submits a new loan application based on credit check request data"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Loan application submitted successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplication.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error during loan application submission")
    })
    @PostMapping("/loan/apply")
    public Mono<ResponseEntity<LoanApplication>> submitLoanApplication(
        @Parameter(description = "Credit check request containing applicant details", required = true)
        @RequestBody CreditCheckRequest request) {
        log.info("Submitting loan application for SSN: {}", request.getSsn());
        
        return creditBureauService.submitLoanApplication(request)
                .map(application -> ResponseEntity.status(HttpStatus.CREATED).body(application))
                .onErrorResume(error -> {
                    log.error("Error submitting loan application", error);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
    
    @Operation(
        summary = "Get Loan Applications by SSN",
        description = "Retrieves all loan applications for a specific SSN"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Loan applications retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplication.class)))
    })
    @GetMapping("/loan/applications/{ssn}")
    public Flux<LoanApplication> getLoanApplications(
        @Parameter(description = "Social Security Number", required = true, example = "123-45-6789")
        @PathVariable String ssn) {
        log.info("Fetching loan applications for SSN: {}", ssn);
        return creditBureauService.getLoanApplicationsBySSN(ssn);
    }
    
    @Operation(
        summary = "Get Loan Application by ID",
        description = "Retrieves a specific loan application by its unique application ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Loan application found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplication.class))),
        @ApiResponse(responseCode = "404", description = "Loan application not found for the given application ID")
    })
    @GetMapping("/loan/application/{applicationId}")
    public Mono<ResponseEntity<LoanApplication>> getLoanApplication(
        @Parameter(description = "Unique loan application identifier", required = true, example = "APP-12345")
        @PathVariable String applicationId) {
        log.info("Fetching loan application: {}", applicationId);
        
        return creditBureauService.getLoanApplicationById(applicationId)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Loan application not found: {}", applicationId, error);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }
}

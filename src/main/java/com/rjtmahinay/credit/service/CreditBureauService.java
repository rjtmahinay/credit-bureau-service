package com.rjtmahinay.credit.service;

import com.rjtmahinay.credit.dto.CreditCheckRequest;
import com.rjtmahinay.credit.dto.CreditCheckResponse;
import com.rjtmahinay.credit.dto.CreateCreditScoreRequest;
import com.rjtmahinay.credit.dto.CreateCreditHistoryRequest;
import com.rjtmahinay.credit.dto.UpdateCreditScoreRequest;
import com.rjtmahinay.credit.dto.UpdateCreditHistoryRequest;
import com.rjtmahinay.credit.model.CreditHistory;
import com.rjtmahinay.credit.model.CreditScore;
import com.rjtmahinay.credit.model.LoanApplication;
import com.rjtmahinay.credit.repository.CreditHistoryRepository;
import com.rjtmahinay.credit.repository.CreditScoreRepository;
import com.rjtmahinay.credit.repository.LoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditBureauService {

    private final CreditScoreRepository creditScoreRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    public Mono<CreditCheckResponse> performCreditCheck(CreditCheckRequest request) {
        log.info("Performing credit check for SSN: {}", request.getSsn());

        return creditScoreRepository.findBySsn(request.getSsn())
                .switchIfEmpty(generateMockCreditScore(request))
                .flatMap(creditScore -> creditHistoryRepository.findActiveAccountsBySsn(request.getSsn())
                        .collectList()
                        .map(history -> buildCreditCheckResponse(creditScore, history, request)));
    }

    public Mono<CreditScore> getCreditScoreBySSN(String ssn) {
        return creditScoreRepository.findBySsn(ssn)
                .switchIfEmpty(Mono.error(new RuntimeException("Credit score not found for SSN: " + ssn)));
    }

    public Flux<CreditHistory> getCreditHistoryBySSN(String ssn) {
        return creditHistoryRepository.findBySsn(ssn);
    }

    public Mono<CreditScore> createCreditScore(CreateCreditScoreRequest request) {
        log.info("Creating credit score for SSN: {}", request.getSsn());

        // Check if credit score already exists for this SSN
        return creditScoreRepository.findBySsn(request.getSsn())
                .flatMap(existingScore -> {
                    log.warn("Credit score already exists for SSN: {}, updating existing record", request.getSsn());
                    return updateExistingCreditScore(existingScore, request);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // Create new credit score
                    String riskLevel = request.getRiskLevel() != null ? request.getRiskLevel()
                            : determineRiskLevel(request.getScore());

                    CreditScore creditScore = CreditScore.builder()
                            .ssn(request.getSsn())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .score(request.getScore())
                            .riskLevel(riskLevel)
                            .createdAt(LocalDateTime.now())
                            .lastUpdated(LocalDateTime.now())
                            .build();

                    return creditScoreRepository.save(creditScore);
                }));
    }

    public Mono<CreditHistory> createCreditHistory(CreateCreditHistoryRequest request) {
        log.info("Creating credit history for SSN: {}", request.getSsn());

        CreditHistory creditHistory = CreditHistory.builder()
                .ssn(request.getSsn())
                .accountType(request.getAccountType())
                .creditorName(request.getCreditorName())
                .originalAmount(request.getOriginalAmount())
                .currentBalance(request.getCurrentBalance())
                .creditLimit(request.getCreditLimit())
                .paymentStatus(request.getPaymentStatus())
                .daysLate(request.getDaysLate() != null ? request.getDaysLate() : 0)
                .accountOpenDate(request.getAccountOpenDate())
                .lastPaymentDate(request.getLastPaymentDate())
                .reportedDate(LocalDateTime.now())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        return creditHistoryRepository.save(creditHistory);
    }

    public Mono<CreditScore> updateCreditScore(String ssn, UpdateCreditScoreRequest request) {
        log.info("Updating credit score for SSN: {}", ssn);

        return creditScoreRepository.findBySsn(ssn)
                .switchIfEmpty(Mono.error(new RuntimeException("Credit score not found for SSN: " + ssn)))
                .flatMap(existingScore -> {
                    String riskLevel = request.getRiskLevel() != null ? request.getRiskLevel()
                            : (request.getScore() != null ? determineRiskLevel(request.getScore())
                                    : existingScore.getRiskLevel());

                    CreditScore updatedScore = CreditScore.builder()
                            .id(existingScore.getId())
                            .ssn(existingScore.getSsn())
                            .firstName(request.getFirstName() != null ? request.getFirstName()
                                    : existingScore.getFirstName())
                            .lastName(
                                    request.getLastName() != null ? request.getLastName() : existingScore.getLastName())
                            .score(request.getScore() != null ? request.getScore() : existingScore.getScore())
                            .riskLevel(riskLevel)
                            .createdAt(existingScore.getCreatedAt())
                            .lastUpdated(LocalDateTime.now())
                            .build();

                    return creditScoreRepository.save(updatedScore);
                });
    }

    public Mono<CreditHistory> updateCreditHistory(Long id, UpdateCreditHistoryRequest request) {
        log.info("Updating credit history record with ID: {}", id);

        return creditHistoryRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Credit history record not found with ID: " + id)))
                .flatMap(existingHistory -> {
                    CreditHistory updatedHistory = CreditHistory.builder()
                            .id(existingHistory.getId())
                            .ssn(existingHistory.getSsn())
                            .accountType(request.getAccountType() != null ? request.getAccountType()
                                    : existingHistory.getAccountType())
                            .creditorName(request.getCreditorName() != null ? request.getCreditorName()
                                    : existingHistory.getCreditorName())
                            .originalAmount(request.getOriginalAmount() != null ? request.getOriginalAmount()
                                    : existingHistory.getOriginalAmount())
                            .currentBalance(request.getCurrentBalance() != null ? request.getCurrentBalance()
                                    : existingHistory.getCurrentBalance())
                            .creditLimit(request.getCreditLimit() != null ? request.getCreditLimit()
                                    : existingHistory.getCreditLimit())
                            .paymentStatus(request.getPaymentStatus() != null ? request.getPaymentStatus()
                                    : existingHistory.getPaymentStatus())
                            .daysLate(request.getDaysLate() != null ? request.getDaysLate()
                                    : existingHistory.getDaysLate())
                            .accountOpenDate(request.getAccountOpenDate() != null ? request.getAccountOpenDate()
                                    : existingHistory.getAccountOpenDate())
                            .lastPaymentDate(request.getLastPaymentDate() != null ? request.getLastPaymentDate()
                                    : existingHistory.getLastPaymentDate())
                            .reportedDate(LocalDateTime.now())
                            .isActive(request.getIsActive() != null ? request.getIsActive()
                                    : existingHistory.getIsActive())
                            .build();

                    return creditHistoryRepository.save(updatedHistory);
                });
    }

    private Mono<CreditScore> updateExistingCreditScore(CreditScore existingScore, CreateCreditScoreRequest request) {
        String riskLevel = request.getRiskLevel() != null ? request.getRiskLevel()
                : determineRiskLevel(request.getScore());

        CreditScore updatedScore = CreditScore.builder()
                .id(existingScore.getId())
                .ssn(existingScore.getSsn())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .score(request.getScore())
                .riskLevel(riskLevel)
                .createdAt(existingScore.getCreatedAt())
                .lastUpdated(LocalDateTime.now())
                .build();

        return creditScoreRepository.save(updatedScore);
    }

    private Mono<CreditScore> generateMockCreditScore(CreditCheckRequest request) {
        // Generate a mock credit score for demo purposes
        int mockScore = 300 + (int) (Math.random() * 550); // Random score between 300-850
        String riskLevel = determineRiskLevel(mockScore);

        CreditScore creditScore = CreditScore.builder()
                .ssn(request.getSsn())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .score(mockScore)
                .riskLevel(riskLevel)
                .lastUpdated(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        return creditScoreRepository.save(creditScore);
    }

    private CreditCheckResponse buildCreditCheckResponse(CreditScore creditScore, List<CreditHistory> history,
            CreditCheckRequest request) {
        String decision = makeDecision(creditScore, history, request);
        BigDecimal approvedAmount = calculateApprovedAmount(creditScore, request);
        BigDecimal interestRate = calculateInterestRate(creditScore, request);

        List<CreditCheckResponse.CreditHistorySummary> historySummary = history.stream()
                .map(h -> CreditCheckResponse.CreditHistorySummary.builder()
                        .accountType(h.getAccountType())
                        .creditorName(h.getCreditorName())
                        .currentBalance(h.getCurrentBalance())
                        .paymentStatus(h.getPaymentStatus())
                        .daysLate(h.getDaysLate())
                        .build())
                .toList();

        return CreditCheckResponse.builder()
                .ssn(creditScore.getSsn())
                .firstName(creditScore.getFirstName())
                .lastName(creditScore.getLastName())
                .creditScore(creditScore.getScore())
                .riskLevel(creditScore.getRiskLevel())
                .isApproved("APPROVED".equals(decision))
                .decision(decision)
                .rejectionReason("REJECTED".equals(decision) ? generateRejectionReason(creditScore, history) : null)
                .approvedAmount(approvedAmount)
                .recommendedInterestRate(interestRate)
                .creditHistory(historySummary)
                .checkDate(LocalDateTime.now())
                .build();
    }

    private String makeDecision(CreditScore creditScore, List<CreditHistory> history, CreditCheckRequest request) {
        int score = creditScore.getScore();

        // Basic decision logic
        if (score >= 700) {
            return "APPROVED";
        } else if (score >= 600) {
            // Check debt-to-income ratio
            BigDecimal debtToIncomeRatio = calculateDebtToIncomeRatio(history, request);
            if (debtToIncomeRatio.compareTo(new BigDecimal("0.4")) <= 0) {
                return "APPROVED";
            } else {
                return "MANUAL_REVIEW";
            }
        } else {
            return "REJECTED";
        }
    }

    private BigDecimal calculateApprovedAmount(CreditScore creditScore, CreditCheckRequest request) {
        BigDecimal requestedAmount = request.getRequestedAmount();
        int score = creditScore.getScore();

        if (score >= 750) {
            return requestedAmount; // Approve full amount
        } else if (score >= 700) {
            return requestedAmount.multiply(new BigDecimal("0.9")); // 90% of requested
        } else if (score >= 650) {
            return requestedAmount.multiply(new BigDecimal("0.75")); // 75% of requested
        } else if (score >= 600) {
            return requestedAmount.multiply(new BigDecimal("0.5")); // 50% of requested
        } else {
            return BigDecimal.ZERO; // Rejected
        }
    }

    private BigDecimal calculateInterestRate(CreditScore creditScore, CreditCheckRequest request) {
        int score = creditScore.getScore();

        if (score >= 750) {
            return new BigDecimal("3.5");
        } else if (score >= 700) {
            return new BigDecimal("5.0");
        } else if (score >= 650) {
            return new BigDecimal("7.5");
        } else if (score >= 600) {
            return new BigDecimal("12.0");
        } else {
            return new BigDecimal("18.0");
        }
    }

    private String determineRiskLevel(int score) {
        if (score >= 700) {
            return "LOW";
        } else if (score >= 600) {
            return "MEDIUM";
        } else {
            return "HIGH";
        }
    }

    private BigDecimal calculateDebtToIncomeRatio(List<CreditHistory> history, CreditCheckRequest request) {
        BigDecimal totalDebt = history.stream()
                .filter(h -> h.getCurrentBalance() != null)
                .map(CreditHistory::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalDebt.divide(request.getAnnualIncome().divide(new BigDecimal("12"), RoundingMode.HALF_UP), 2,
                RoundingMode.HALF_UP);
    }

    private String generateRejectionReason(CreditScore creditScore, List<CreditHistory> history) {
        if (creditScore.getScore() < 500) {
            return "Credit score too low";
        }

        long negativeAccounts = history.stream()
                .filter(h -> !"CURRENT".equals(h.getPaymentStatus()))
                .count();

        if (negativeAccounts > 2) {
            return "Too many delinquent accounts";
        }

        return "Credit profile does not meet lending criteria";
    }
}

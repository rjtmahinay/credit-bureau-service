package com.rjtmahinay.credit.repository;

import com.rjtmahinay.credit.model.LoanApplication;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LoanApplicationRepository extends ReactiveCrudRepository<LoanApplication, Long> {
    
    Mono<LoanApplication> findByApplicationId(String applicationId);
    
    Flux<LoanApplication> findBySsn(String ssn);
    
    Flux<LoanApplication> findByStatus(String status);
    
    @Query("SELECT * FROM loan_applications WHERE ssn = :ssn AND status = :status ORDER BY application_date DESC")
    Flux<LoanApplication> findBySsnAndStatus(String ssn, String status);
    
    @Query("SELECT * FROM loan_applications WHERE status = 'PENDING' ORDER BY application_date ASC")
    Flux<LoanApplication> findPendingApplications();
}

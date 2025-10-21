package com.rjtmahinay.credit.repository;

import com.rjtmahinay.credit.model.CreditScore;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CreditScoreRepository extends ReactiveCrudRepository<CreditScore, Long> {
    
    Mono<CreditScore> findBySsn(String ssn);
    
    @Query("SELECT * FROM credit_scores WHERE ssn = :ssn AND last_updated > NOW() - INTERVAL '30 days'")
    Mono<CreditScore> findRecentCreditScoreBySsn(String ssn);
    
    @Query("SELECT * FROM credit_scores WHERE first_name = :firstName AND last_name = :lastName")
    Mono<CreditScore> findByFirstNameAndLastName(String firstName, String lastName);
}

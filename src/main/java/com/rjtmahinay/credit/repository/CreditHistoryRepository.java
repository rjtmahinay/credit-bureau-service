package com.rjtmahinay.credit.repository;

import com.rjtmahinay.credit.model.CreditHistory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditHistoryRepository extends ReactiveCrudRepository<CreditHistory, Long> {
    
    Flux<CreditHistory> findBySsn(String ssn);
    
    Flux<CreditHistory> findBySsnAndIsActive(String ssn, Boolean isActive);
    
    @Query("SELECT * FROM credit_history WHERE ssn = :ssn AND payment_status != 'CURRENT' ORDER BY days_late DESC")
    Flux<CreditHistory> findNegativeHistoryBySsn(String ssn);
    
    @Query("SELECT * FROM credit_history WHERE ssn = :ssn AND is_active = true ORDER BY reported_date DESC")
    Flux<CreditHistory> findActiveAccountsBySsn(String ssn);
}

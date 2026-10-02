package com.bank.loans.domain.port;

import com.bank.loans.domain.model.Loan;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepositoryPort {
    Optional<Loan> findById(UUID id);
    Loan save(Loan loan);
    void deleteById(UUID id);
    Optional<Loan> findByCustomerIdAndId(UUID customerId, UUID loanId);
}
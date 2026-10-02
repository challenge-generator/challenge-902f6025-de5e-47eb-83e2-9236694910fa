package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import com.bank.loans.domain.port.LoanRepositoryPort;
import com.bank.loans.infrastructure.repository.LoanJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanJpaRepositoryAdapter implements LoanRepositoryPort {

    private final LoanJpaRepository loanJpaRepository;

    public LoanJpaRepositoryAdapter(LoanJpaRepository loanJpaRepository) {
        this.loanJpaRepository = loanJpaRepository;
    }

    @Override
    public Optional<Loan> findById(UUID id) {
        return loanJpaRepository.findById(id);
    }

    @Override
    public Loan save(Loan loan) {
        return loanJpaRepository.save(loan);
    }

    @Override
    public void deleteById(UUID id) {
        loanJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Loan> findByCustomerIdAndId(UUID customerId, UUID loanId) {
        return loanJpaRepository.findByCustomerIdAndId(customerId, loanId);
    }
}
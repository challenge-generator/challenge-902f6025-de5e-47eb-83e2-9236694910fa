package com.bank.loans.infrastructure.repository;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Loan> findById(UUID id) {
        LoanEntity entity = entityManager.find(LoanEntity.class, id);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    public Loan save(Loan loan) {
        LoanEntity entity = toEntity(loan);
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return toDomain(entity);
        } else {
            entityManager.merge(entity);
            return toDomain(entity);
        }
    }

    public void deleteById(UUID id) {
        LoanEntity entity = entityManager.find(LoanEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public Optional<Loan> findByCustomerIdAndId(UUID customerId, UUID loanId) {
        try {
            LoanEntity entity = entityManager
                .createQuery("SELECT l FROM LoanEntity l WHERE l.id = :loanId AND l.customerId = :customerId", LoanEntity.class)
                .setParameter("loanId", loanId)
                .setParameter("customerId", customerId)
                .getSingleResult();
            return Optional.of(toDomain(entity));
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    private Loan toDomain(LoanEntity entity) {
        return new Loan(
            entity.getId(),
            entity.getCustomerId(),
            entity.getAmount(),
            entity.getInterestRate(),
            entity.getTermMonths(),
            LoanStatus.valueOf(entity.getStatus()),
            entity.getApprovedAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    private LoanEntity toEntity(Loan loan) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.id());
        entity.setCustomerId(loan.customerId());
        entity.setAmount(loan.amount());
        entity.setInterestRate(loan.interestRate());
        entity.setTermMonths(loan.termMonths());
        entity.setStatus(loan.status().name());
        entity.setApprovedAt(loan.approvedAt());
        entity.setCreatedAt(loan.createdAt());
        entity.setUpdatedAt(loan.updatedAt());
        return entity;
    }

    @Entity
    @Table(name = "loans")
    public static class LoanEntity {
        @Id
        private UUID id;

        @Column(name = "customer_id", nullable = false)
        private UUID customerId;

        @Column(name = "amount", nullable = false)
        private java.math.BigDecimal amount;

        @Column(name = "interest_rate", nullable = false)
        private java.math.BigDecimal interestRate;

        @Column(name = "term_months", nullable = false)
        private Integer termMonths;

        @Column(name = "status", nullable = false)
        private String status;

        @Column(name = "approved_at")
        private LocalDate approvedAt;

        @Column(name = "created_at", nullable = false)
        private LocalDate createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDate updatedAt;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public UUID getCustomerId() { return customerId; }
        public void setCustomerId(UUID customerId) { this.customerId = customerId; }
        public java.math.BigDecimal getAmount() { return amount; }
        public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }
        public java.math.BigDecimal getInterestRate() { return interestRate; }
        public void setInterestRate(java.math.BigDecimal interestRate) { this.interestRate = interestRate; }
        public Integer getTermMonths() { return termMonths; }
        public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDate getApprovedAt() { return approvedAt; }
        public void setApprovedAt(LocalDate approvedAt) { this.approvedAt = approvedAt; }
        public LocalDate getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
        public LocalDate getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }
    }
}
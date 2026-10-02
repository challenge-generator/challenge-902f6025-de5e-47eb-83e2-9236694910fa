package com.bank.loans.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record Loan(
    @NotNull(message = "Loan ID cannot be null")
    UUID id,

    @NotNull(message = "Customer ID cannot be null")
    UUID customerId,

    @NotNull(message = "Loan amount cannot be null")
    @Positive(message = "Loan amount must be positive")
    BigDecimal amount,

    @NotNull(message = "Interest rate cannot be null")
    @PositiveOrZero(message = "Interest rate must be zero or positive")
    BigDecimal interestRate,

    @NotNull(message = "Start date cannot be null")
    LocalDate startDate,

    @NotNull(message = "End date cannot be null")
    LocalDate endDate,

    @NotNull(message = "Status cannot be null")
    LoanStatus status,

    @NotNull(message = "Creation date cannot be null")
    LocalDate createdAt,

    LocalDate updatedAt
) {
    public enum LoanStatus {
        ACTIVE, PAID, OVERDUE, REJECTED
    }

    public Loan {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }

    public Loan withUpdatedAt(LocalDate updatedAt) {
        return new Loan(id, customerId, amount, interestRate, startDate, endDate, status, createdAt, updatedAt);
    }
}
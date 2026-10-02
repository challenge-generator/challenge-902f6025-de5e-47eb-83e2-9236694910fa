package com.bank.loans.infrastructure.validator;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class LoanValidator {

    private static final BigDecimal MIN_LOAN_AMOUNT = new BigDecimal("1000.00");
    private static final BigDecimal MAX_LOAN_AMOUNT = new BigDecimal("500000.00");
    private static final int MIN_TERM_MONTHS = 1;
    private static final int MAX_TERM_MONTHS = 360;
    private static final BigDecimal MIN_INTEREST_RATE = new BigDecimal("0.01");
    private static final BigDecimal MAX_INTEREST_RATE = new BigDecimal("0.50");
    private static final BigDecimal MAX_MONTHLY_PAYMENT_RATIO = new BigDecimal("0.60");

    public ValidationResult validate(Loan loan) {
        List<String> errors = new ArrayList<>();
        
        if (loan == null) {
            errors.add("El préstamo no puede ser nulo");
            return new ValidationResult(false, errors);
        }
        
        validateLoanId(loan.id(), errors);
        validateCustomerId(loan.customerId(), errors);
        validateAmount(loan.amount(), errors);
        validateTerm(loan.termMonths(), errors);
        validateInterestRate(loan.interestRate(), errors);
        validateStatus(loan.status(), errors);
        validateDates(loan.issueDate(), loan.dueDate(), errors);
        validateMonthlyPayment(loan.amount(), loan.termMonths(), loan.interestRate(), 
                               loan.monthlyIncome() != null ? loan.monthlyIncome() : BigDecimal.ZERO, errors);
        
        return new ValidationResult(errors.isEmpty(), errors);
    }

    private void validateLoanId(UUID loanId, List<String> errors) {
        if (loanId == null) {
            errors.add("El ID del préstamo es obligatorio");
        }
    }

    private void validateCustomerId(UUID customerId, List<String> errors) {
        if (customerId == null) {
            errors.add("El ID del cliente es obligatorio");
        }
    }

    private void validateAmount(BigDecimal amount, List<String> errors) {
        if (amount == null) {
            errors.add("El monto del préstamo es obligatorio");
            return;
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("El monto del préstamo debe ser mayor a cero");
        }
        
        if (amount.compareTo(MIN_LOAN_AMOUNT) < 0) {
            errors.add(String.format("El monto mínimo del préstamo es %s", MIN_LOAN_AMOUNT));
        }
        
        if (amount.compareTo(MAX_LOAN_AMOUNT) > 0) {
            errors.add(String.format("El monto máximo del préstamo es %s", MAX_LOAN_AMOUNT));
        }
    }

    private void validateTerm(int termMonths, List<String> errors) {
        if (termMonths <= 0) {
            errors.add("El plazo del préstamo debe ser mayor a cero");
            return;
        }
        
        if (termMonths < MIN_TERM_MONTHS) {
            errors.add(String.format("El plazo mínimo del préstamo es %d meses", MIN_TERM_MONTHS));
        }
        
        if (termMonths > MAX_TERM_MONTHS) {
            errors.add(String.format("El plazo máximo del préstamos es %d meses", MAX_TERM_MONTHS));
        }
    }

    private void validateInterestRate(BigDecimal interestRate, List<String> errors) {
        if (interestRate == null) {
            errors.add("La tasa de interés es obligatoria");
            return;
        }
        
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("La tasa de interés debe ser mayor a cero");
        }
        
        if (interestRate.compareTo(MIN_INTEREST_RATE) < 0) {
            errors.add(String.format("La tasa mínima de interés es %.2f%%", 
                MIN_INTEREST_RATE.multiply(BigDecimal.valueOf(100))));
        }
        
        if (interestRate.compareTo(MAX_INTEREST_RATE) > 0) {
            errors.add(String.format("La tasa máxima de interés es %.2f%%", 
                MAX_INTEREST_RATE.multiply(BigDecimal.valueOf(100))));
        }
    }

    private void validateStatus(LoanStatus status, List<String> errors) {
        if (status == null) {
            errors.add("El estado del préstamo es obligatorio");
        }
    }

    private void validateDates(LocalDate issueDate, LocalDate dueDate, List<String> errors) {
        LocalDate today = LocalDate.now();
        
        if (issueDate == null) {
            errors.add("La fecha de emisión es obligatoria");
        } else if (issueDate.isBefore(today.minusDays(1))) {
            errors.add("La fecha de emisión no puede ser anterior a hoy");
        }
        
        if (dueDate == null) {
            errors.add("La fecha de vencimiento es obligatoria");
        } else if (issueDate != null && dueDate.isBefore(issueDate)) {
            errors.add("La fecha de vencimiento no puede ser anterior a la fecha de emisión");
        } else if (dueDate.isBefore(today)) {
            errors.add("El préstamo ya está vencido");
        }
    }

    private void validateMonthlyPayment(BigDecimal amount, int termMonths, 
            BigDecimal interestRate, BigDecimal monthlyIncome, List<String> errors) {
        if (amount == null || termMonths <= 0 || interestRate == null || monthlyIncome == null) {
            return;
        }
        
        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, termMonths, interestRate);
        BigDecimal paymentRatio = monthlyPayment.divide(monthlyIncome, 4, java.math.RoundingMode.HALF_UP);
        
        if (paymentRatio.compareTo(MAX_MONTHLY_PAYMENT_RATIO) > 0) {
            errors.add(String.format("La cuota mensual (%.2f) excede el %.0f%% del ingreso mensual (%.2f)",
                monthlyPayment, 
                MAX_MONTHLY_PAYMENT_RATIO.multiply(BigDecimal.valueOf(100)),
                monthlyIncome));
        }
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal principal, int termMonths, BigDecimal annualRate) {
        if (principal == null || termMonths <= 0 || annualRate == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 6, java.math.RoundingMode.HALF_UP);
        
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(termMonths), 2, java.math.RoundingMode.HALF_UP);
        }
        
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        BigDecimal numerator = monthlyRate.multiply(onePlusR.pow(termMonths));
        BigDecimal denominator = onePlusR.pow(termMonths).subtract(BigDecimal.ONE);
        
        return principal.multiply(numerator).divide(denominator, 2, java.math.RoundingMode.HALF_UP);
    }

    public boolean canChangeStatus(Loan currentLoan, LoanStatus newStatus) {
        if (currentLoan == null || currentLoan.status() == null || newStatus == null) {
            return false;
        }
        
        return switch (currentLoan.status()) {
            case PENDING -> newStatus == LoanStatus.APPROVED || newStatus == LoanStatus.REJECTED;
            case APPROVED -> newStatus == LoanStatus.ACTIVE || newStatus == LoanStatus.REJECTED;
            case ACTIVE -> newStatus == LoanStatus.PAID || newStatus == LoanStatus.DEFAULTED || newStatus == LoanStatus.CANCELLED;
            case PAID, DEFAULTED, CANCELLED, REJECTED -> false;
        };
    }

    public record ValidationResult(boolean valid, List<String> errors) {
        public boolean hasErrors() {
            return !valid;
        }
        
        public String getErrorMessage() {
            return valid ? "" : String.join("; ", errors);
        }
    }
}
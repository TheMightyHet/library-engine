package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.model.Loan;
import java.util.List;

public interface LoanService {
    Loan getLoan(Long id);
    List<Loan> getActiveLoansForReader(Long readerId);
    Loan getActiveLoanForBook(Long bookId);
    List<Loan> getOverdueLoans();
    Loan borrowBook(Long bookId, Long readerId, int loanDurationDays);
    Loan returnBook(Long bookId);
}

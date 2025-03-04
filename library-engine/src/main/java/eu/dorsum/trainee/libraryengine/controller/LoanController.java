package eu.dorsum.trainee.libraryengine.controller;

import eu.dorsum.trainee.libraryengine.model.Loan;
import eu.dorsum.trainee.libraryengine.service.BookService;
import eu.dorsum.trainee.libraryengine.service.LoanService;
import eu.dorsum.trainee.libraryengine.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final ReaderService readerService;

    public LoanController(LoanService loanService, BookService bookService, ReaderService readerService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping("/getLoanByID/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {
        Loan loan = loanService.getLoan(id);
        return loan != null ? ResponseEntity.ok(loan) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getActiveLoansForReaderByID/{readerId}")
    public ResponseEntity<List<Loan>> getActiveLoansForReader(@PathVariable Long readerId) {
        if (readerService.getReader(readerId) == null) {
            return ResponseEntity.notFound().build();
        }

        List<Loan> loans = loanService.getActiveLoansForReader(readerId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/getActiveLoanForBookByID/{bookId}")
    public ResponseEntity<Loan> getActiveLoanForBook(@PathVariable Long bookId) {
        if (bookService.getBook(bookId) == null) {
            return ResponseEntity.notFound().build();
        }

        Loan loan = loanService.getActiveLoanForBook(bookId);
        return loan != null ? ResponseEntity.ok(loan) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getOverdueLoans")
    public List<Loan> getOverdueLoans() {
        return loanService.getOverdueLoans();
    }

    @PostMapping("/borrowBook")
    public ResponseEntity<?> borrowBook(@RequestParam Long bookId, @RequestParam Long readerId, @RequestParam(defaultValue = "14") int loanDurationDays) {
        try {
            if (bookService.getBook(bookId) == null) {
                return ResponseEntity.badRequest().body("Book not found");
            }

            if (readerService.getReader(readerId) == null) {
                return ResponseEntity.badRequest().body("Reader not found");
            }

            Loan loan = loanService.borrowBook(bookId, readerId, loanDurationDays);
            return ResponseEntity.status(HttpStatus.CREATED).body(loan);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/returnBookByID/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        try {
            if (bookService.getBook(bookId) == null) {
                return ResponseEntity.badRequest().body("Book not found");
            }

            Loan loan = loanService.returnBook(bookId);
            return ResponseEntity.ok(loan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
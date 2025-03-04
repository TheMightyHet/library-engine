package eu.dorsum.trainee.libraryengine.controller;

import eu.dorsum.trainee.libraryengine.model.Book;
import eu.dorsum.trainee.libraryengine.model.BookStatus;
import eu.dorsum.trainee.libraryengine.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getBookByID/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getBooksByStatus/{status}")
    public List<Book> getBooksByStatus(@PathVariable String status) {
        return bookService.getBooksByStatus(status);
    }

    @GetMapping("/getAvailableBooks")
    public List<Book> getAvailableBooks() {
        return bookService.getBooksByStatus(BookStatus.AVAILABLE);
    }

    @PostMapping("createBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/updateBookByID/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book existingBook = bookService.getBook(id);
        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }

        book.setId(id);
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/deleteBookByID/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book existingBook = bookService.getBook(id);
        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }

        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

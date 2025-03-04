package eu.dorsum.trainee.libraryengine.controller;

import eu.dorsum.trainee.libraryengine.model.ReaderCard;
import eu.dorsum.trainee.libraryengine.service.ReaderCardService;
import eu.dorsum.trainee.libraryengine.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reader_cards")
public class ReaderCardController {

    private final ReaderCardService readerCardService;
    private final ReaderService readerService;

    public ReaderCardController(ReaderCardService readerCardService, ReaderService readerService) {
        this.readerCardService = readerCardService;
        this.readerService = readerService;
    }

    @GetMapping("/getReaderCardById/{id}")
    public ResponseEntity<ReaderCard> getReaderCard(@PathVariable Long id) {
        ReaderCard readerCard = readerCardService.getReaderCard(id);
        return readerCard != null ? ResponseEntity.ok(readerCard) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getReaderCardsByReaderID/{readerId}")
    public ResponseEntity<List<ReaderCard>> getReaderCardsByReaderId(@PathVariable Long readerId) {
        if (readerService.getReader(readerId) == null) {
            return ResponseEntity.notFound().build();
        }

        List<ReaderCard> readerCards = readerCardService.getReaderCardsByReaderId(readerId);
        return ResponseEntity.ok(readerCards);
    }

    @GetMapping("/getValidCardForReaderByID/{readerId}")
    public ResponseEntity<ReaderCard> getValidCardForReader(@PathVariable Long readerId) {
        if (readerService.getReader(readerId) == null) {
            return ResponseEntity.notFound().build();
        }

        ReaderCard validCard = readerCardService.getValidCardForReader(readerId);
        return validCard != null ? ResponseEntity.ok(validCard) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getReaderCardExpirationDateByReaderID/{readerId}")
    public ResponseEntity<Date> getReaderCardExpirationDate(@PathVariable Long readerId) {
        if (readerService.getReader(readerId) == null) {
            return ResponseEntity.notFound().build();
        }

        Date expirationDate = readerCardService.getReaderCardExpirationDate(readerId);
        return expirationDate != null ? ResponseEntity.ok(expirationDate) : ResponseEntity.notFound().build();
    }

    @PostMapping("/createReaderCard")
    public ResponseEntity<ReaderCard> createReaderCard(@RequestBody ReaderCard readerCard) {
        if (readerService.getReader(readerCard.getReaderId()) == null) {
            return ResponseEntity.badRequest().build();
        }

        ReaderCard createdReaderCard = readerCardService.createReaderCard(readerCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReaderCard);
    }

    @PutMapping("/updateReaderCardByID/{id}")
    public ResponseEntity<ReaderCard> updateReaderCard(@PathVariable Long id, @RequestBody ReaderCard readerCard) {
        ReaderCard existingReaderCard = readerCardService.getReaderCard(id);
        if (existingReaderCard == null) {
            return ResponseEntity.notFound().build();
        }

        readerCard.setId(id);
        ReaderCard updatedReaderCard = readerCardService.updateReaderCard(readerCard);
        return ResponseEntity.ok(updatedReaderCard);
    }

    @DeleteMapping("/deleteReaderCardByID/{id}")
    public ResponseEntity<Void> deleteReaderCard(@PathVariable Long id) {
        ReaderCard existingReaderCard = readerCardService.getReaderCard(id);
        if (existingReaderCard == null) {
            return ResponseEntity.notFound().build();
        }

        readerCardService.deleteReaderCard(id);
        return ResponseEntity.noContent().build();
    }
}

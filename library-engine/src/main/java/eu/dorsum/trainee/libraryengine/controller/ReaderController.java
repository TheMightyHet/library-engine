package eu.dorsum.trainee.libraryengine.controller;

import eu.dorsum.trainee.libraryengine.model.Reader;
import eu.dorsum.trainee.libraryengine.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/getReader/{id}")
    public ResponseEntity<Reader> getReader(@PathVariable Long id) {
        Reader reader = readerService.getReader(id);
        return reader != null ? ResponseEntity.ok(reader) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllReaders")
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @PostMapping("/createReader")
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        Reader createdReader = readerService.createReader(reader);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReader);
    }

    @PutMapping("/updateReaderByID/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        Reader existingReader = readerService.getReader(id);
        if (existingReader == null) {
            return ResponseEntity.notFound().build();
        }

        reader.setId(id);
        Reader updatedReader = readerService.updateReader(reader);
        return ResponseEntity.ok(updatedReader);
    }

    @DeleteMapping("/deleteReaderByID/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        Reader existingReader = readerService.getReader(id);
        if (existingReader == null) {
            return ResponseEntity.notFound().build();
        }

        readerService.deleteReader(id);
        return ResponseEntity.noContent().build();
    }
}
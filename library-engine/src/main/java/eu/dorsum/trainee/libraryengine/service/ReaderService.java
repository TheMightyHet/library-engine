package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.model.Reader;
import java.util.List;

public interface ReaderService {
    Reader getReader(Long id);
    List<Reader> getAllReaders();
    Reader createReader(Reader reader);
    Reader updateReader(Reader reader);
    void deleteReader(Long id);
}

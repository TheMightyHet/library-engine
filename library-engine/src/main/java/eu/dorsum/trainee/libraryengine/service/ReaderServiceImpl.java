// src/main/java/eu/dorsum/trainee/libraryengine/service/impl/ReaderServiceImpl.java
package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.ReaderDao;
import eu.dorsum.trainee.libraryengine.model.Reader;
import eu.dorsum.trainee.libraryengine.service.ReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    private static final Logger LOG = LoggerFactory.getLogger(ReaderServiceImpl.class);

    private final ReaderDao readerDao;

    public ReaderServiceImpl(ReaderDao readerDao) {
        this.readerDao = readerDao;
    }

    @Override
    public Reader getReader(Long id) {
        return readerDao.findById(id);
    }

    @Override
    public List<Reader> getAllReaders() {
        LOG.info("Fetching all readers: {}", readerDao.findAll().toString());
        return readerDao.findAll();
    }

    @Override
    @Transactional
    public Reader createReader(Reader reader) {
        LOG.info("Creating reader: {}", reader.getName());
        readerDao.insert(reader);
        return reader;
    }

    @Override
    @Transactional
    public Reader updateReader(Reader reader) {
        readerDao.update(reader);
        return reader;
    }

    @Override
    @Transactional
    public void deleteReader(Long id) {
        readerDao.delete(id);
    }
}
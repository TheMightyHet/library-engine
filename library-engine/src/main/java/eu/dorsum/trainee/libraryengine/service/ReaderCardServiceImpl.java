package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.ReaderCardDao;
import eu.dorsum.trainee.libraryengine.model.ReaderCard;
import eu.dorsum.trainee.libraryengine.service.ReaderCardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReaderCardServiceImpl implements ReaderCardService {

    private final ReaderCardDao readerCardDao;

    public ReaderCardServiceImpl(ReaderCardDao readerCardDao) {
        this.readerCardDao = readerCardDao;
    }

    @Override
    public ReaderCard getReaderCard(Long id) {
        return readerCardDao.findById(id);
    }

    @Override
    public List<ReaderCard> getReaderCardsByReaderId(Long readerId) {
        return readerCardDao.findByReaderId(readerId);
    }

    @Override
    public ReaderCard getValidCardForReader(Long readerId) {
        return readerCardDao.findValidCardByReaderId(readerId);
    }

    @Override
    @Transactional
    public ReaderCard createReaderCard(ReaderCard readerCard) {
        readerCardDao.insert(readerCard);
        return readerCard;
    }

    @Override
    @Transactional
    public ReaderCard updateReaderCard(ReaderCard readerCard) {
        readerCardDao.update(readerCard);
        return readerCard;
    }

    @Override
    @Transactional
    public void deleteReaderCard(Long id) {
        readerCardDao.delete(id);
    }

    @Override
    public boolean isReaderCardValid(Long readerId) {
        return getValidCardForReader(readerId) != null;
    }

    @Override
    public Date getReaderCardExpirationDate(Long readerId) {
        ReaderCard card = getValidCardForReader(readerId);
        return card != null ? card.getValidUntil() : null;
    }
}

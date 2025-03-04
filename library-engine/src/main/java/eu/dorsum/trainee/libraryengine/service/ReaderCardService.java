package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.model.ReaderCard;
import java.util.Date;
import java.util.List;

public interface ReaderCardService {
    ReaderCard getReaderCard(Long id);
    List<ReaderCard> getReaderCardsByReaderId(Long readerId);
    ReaderCard getValidCardForReader(Long readerId);
    ReaderCard createReaderCard(ReaderCard readerCard);
    ReaderCard updateReaderCard(ReaderCard readerCard);
    void deleteReaderCard(Long id);
    boolean isReaderCardValid(Long readerId);
    Date getReaderCardExpirationDate(Long readerId);
}

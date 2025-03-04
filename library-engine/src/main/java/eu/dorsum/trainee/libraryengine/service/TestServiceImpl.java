package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.TestDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger LOG = LoggerFactory.getLogger(TestServiceImpl.class);

    private final TestDao testDao;

    public TestServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }


    @Override
    public int getTest1() {
        LOG.info("getTest1 started");
        return testDao.getOne();
    }

    @Override
    public int getTest2() {
        return testDao.getTwo();
    }
}

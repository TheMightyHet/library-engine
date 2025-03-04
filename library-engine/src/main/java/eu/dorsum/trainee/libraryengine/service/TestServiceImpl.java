package eu.dorsum.trainee.libraryengine.service;

import eu.dorsum.trainee.libraryengine.dao.TestDao;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final TestDao testDao;

    public TestServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }


    @Override
    public int getTest1() {
        return testDao.getOne();
    }

    @Override
    public int getTest2() {
        return testDao.getTwo();
    }
}

package eu.dorsum.trainee.libraryengine.controller;

import eu.dorsum.trainee.libraryengine.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test1")
    public int getTest1() {
        return testService.getTest1();
    }

    @GetMapping("/test2")
    public int getTest2() {
        return testService.getTest2();
    }

}

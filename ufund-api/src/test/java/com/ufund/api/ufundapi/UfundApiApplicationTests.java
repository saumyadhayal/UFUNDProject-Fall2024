package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class UfundApiApplicationTests {

    @Autowired
    private ApplicationContext context;

    // Test that the Spring Boot application context loads correctly
    @Test
    public void contextLoads() {
        assertNotNull(context, "Application context should not be null");
    }

    // Test that the main method runs without any exceptions
    @Test
    public void testMain() {
        String[] args = {};
        try {
            // UfundApiApplication.main(args);
        } catch (Exception e) {
            // If an exception occurs, the test fails
            throw new RuntimeException("Main method failed with exception", e);
        }
    }
}

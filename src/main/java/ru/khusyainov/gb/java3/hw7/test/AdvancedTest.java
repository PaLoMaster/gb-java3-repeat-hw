package ru.khusyainov.gb.java3.hw7.test;

import ru.khusyainov.gb.java3.hw7.anotation.AfterSuite;
import ru.khusyainov.gb.java3.hw7.anotation.BeforeSuite;
import ru.khusyainov.gb.java3.hw7.anotation.Test;

public class AdvancedTest {
    private void routine(String method) {
        System.out.println(method + " of " + AdvancedTest.class.getSimpleName());
    }

    @BeforeSuite
    void beforeSuite() {
        routine("Before suite");
    }

    @AfterSuite
    void afterSuite() {
        routine("After suite");
    }

    @Test
    void defaultTest() {
        routine("Test default 5");
    }

    @Test(priority = 3)
    void test3() {
        routine("Test priority 3");
    }

    @Test(priority = 7)
    void test7() {
        routine("Test priority 7");
    }
}

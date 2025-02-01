package ru.khusyainov.gb.java3.hw7.test;

import ru.khusyainov.gb.java3.hw7.anotation.AfterSuite;
import ru.khusyainov.gb.java3.hw7.anotation.BeforeSuite;
import ru.khusyainov.gb.java3.hw7.anotation.Test;

public class FaultyTestAfter {
    private void routine(String method) {
        System.out.println(method + " of " + FaultyTestAfter.class.getSimpleName());
    }

    @BeforeSuite
    void beforeSuite() {
        routine("Before Suite");
    }

    @AfterSuite
    void afterSuite1() {
        routine("After Suite 1");
    }

    @AfterSuite
    void afterSuite2() {
        routine("After Suite 2");
    }
}

package ru.khusyainov.gb.java3.hw7.test;

import ru.khusyainov.gb.java3.hw7.anotation.AfterSuite;
import ru.khusyainov.gb.java3.hw7.anotation.BeforeSuite;
import ru.khusyainov.gb.java3.hw7.anotation.Test;

public class FaultyTestBefore {
    private void routine(String method) {
        System.out.println(method + " of " + FaultyTestBefore.class.getSimpleName());
    }

    @BeforeSuite
    void beforeSuite1() {
        routine("Before Suite 1");
    }

    @BeforeSuite
    void beforeSuite2() {
        routine("Before Suite 2");
    }

    @AfterSuite
    void afterSuite2() {
        routine("After Suite 2");
    }
}

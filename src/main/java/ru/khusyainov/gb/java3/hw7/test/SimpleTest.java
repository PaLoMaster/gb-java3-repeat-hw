package ru.khusyainov.gb.java3.hw7.test;

import ru.khusyainov.gb.java3.hw7.anotation.Test;

public class SimpleTest {
    private void routine(String method) {
        System.out.println(method + " of " + SimpleTest.class.getSimpleName());
    }

    @Test
    void testDefault1() {
        routine("Test default 5.1");
    }

    @Test
    void testDefault2() {
        routine("Test default 5.2");
    }

    @Test(priority = 1)
    void test11() {
        routine("Test priority 1.1");
    }

    @Test(priority = 1)
    void test12() {
        routine("Test priority 1.2");
    }

    @Test(priority = 5)
    void test5() {
        routine("Test priority 5");
    }

    @Test(priority = 8)
    void test8() {
        routine("Test priority 8");
    }

    @Test(priority = 10)
    void test10() {
        routine("Test priority 10");
    }

    @Test(priority = 100)
    void test100() {
        routine("Test priority 100");
        System.out.println("I'm didn't find out - can i limit value or not. It was especially interesting to see " +
                "the code of the @Order annotation in Log4j2)");
    }
}

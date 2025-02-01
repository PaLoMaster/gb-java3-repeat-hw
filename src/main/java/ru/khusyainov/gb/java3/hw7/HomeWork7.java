package ru.khusyainov.gb.java3.hw7;

import ru.khusyainov.gb.java3.hw7.test.AdvancedTest;
import ru.khusyainov.gb.java3.hw7.test.FaultyTestBefore;
import ru.khusyainov.gb.java3.hw7.test.SimpleTest;

public class HomeWork7 {
    public static void main(String[] args) {
        System.out.println("Simple tests by priority...");
        Tester.start(SimpleTest.class);
        System.out.println("\n\nAdvanced tests...");
        Tester.start(AdvancedTest.class);
        System.out.println("\n\nFaulty tests...\n");
        try {
            Tester.start(FaultyTestBefore.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        try {
            Tester.start(ru.khusyainov.gb.java3.hw7.test.FaultyTestAfter.class.getName());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

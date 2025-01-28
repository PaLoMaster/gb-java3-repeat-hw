package ru.khusyainov.gb.java3.hw6.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.khusyainov.gb.java3.hw6.service.TestHelper;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentTest {
    private final Logger LOGGER = LogManager.getLogger(StudentTest.class);
    private int testId = 0;
    private String testSurname = TestHelper.getTestCurrentTime();
    private float testScore = 0;
    Student testStudent = new Student(testId, testSurname, testScore);

    @Test
    @Order(2)
    void getId() {
        assertEquals(testId, testStudent.getId());
    }

    @Test
    @Order(3)
    void setId() {
        testId = TestHelper.getRandomTestId();
        testStudent.setId(testId);
        assertEquals(testId, testStudent.getId());
    }

    @Test
    @Order(4)
    void getSurname() {
        assertEquals(testSurname, testStudent.getSurname());
    }

    @Test
    @Order(5)
    void setSurname() {
        testSurname = TestHelper.getTestCurrentTime();
        testStudent.setSurname(testSurname);
        assertEquals(testSurname, testStudent.getSurname());
    }

    @Test
    @Order(6)
    void getScore() {
        assertEquals(testScore, testStudent.getScore());
    }

    @Test
    @Order(7)
    void setScore() {
        testScore = (float) Math.random();
        testStudent.setScore(testScore);
        assertEquals(testScore, testStudent.getScore());
    }

    private void testEquals() {
        Student expect = new Student(testId, testSurname, testScore);
        assertEquals(expect, testStudent);
        LOGGER.info(testStudent);
    }

    @Test
    @Order(1)
    void testEquals0() {
        testEquals();
    }

    @Test
    @Order(8)
    void testEquals1() {
        testEquals();
    }

    private void testHashCode() {
        int expect = testStudent.hashCode();
        int actual = testStudent.hashCode();
        assertEquals(expect, actual);
        LOGGER.info("Test student: {}, hash1: {}, hash2: {}.", testStudent, expect, actual);
    }

    @Test
    @Order(1)
    void testHashCode0() {
        testHashCode();
    }

    @Test
    @Order(8)
    void testHashCode1() {
        testHashCode();
    }

    private void testToString() {
        String test = testStudent.toString();
        assertTrue(test.contains(String.valueOf(testId)));
        assertTrue(test.contains(String.valueOf(testSurname)));
        assertTrue(test.contains(String.valueOf(testScore)));
        LOGGER.info("Test student string: {}, id: {}, surname: {}, score: {}.",
                testStudent, testId, testSurname, testScore);
    }

    @Test
    @Order(1)
    void testToString0() {
        testToString();
    }

    @Test
    @Order(8)
    void testToString1() {
        testToString();
    }
}
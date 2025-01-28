package ru.khusyainov.gb.java3.hw6.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;

@TestMethodOrder(OrderAnnotation.class)
class ConManTest {
    private static Connection conn;

    @BeforeAll
    static void beforeAll() {
        try {
            conn = ConMan.getConnection();
        } catch (SQLException e) {
            logException(e);
        }
    }

    @Test
    @Order(1)
    void getConnection() {
        try {
            assertTrue(conn != null && conn.isValid(1));
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(2)
    void closeConnection() {
        try {
            ConMan.closeConnection();
            assertTrue(conn != null && conn.isClosed());
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }
}
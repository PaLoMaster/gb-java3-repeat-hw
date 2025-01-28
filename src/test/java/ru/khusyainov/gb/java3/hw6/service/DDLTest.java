package ru.khusyainov.gb.java3.hw6.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DDLTest {
    private static final Logger LOGGER = LogManager.getLogger(DDLTest.class);

    @BeforeAll
    void beforeAll() {
        TestHelper.renameTableFile();
    }

    @Test
    @Order(1)
    void createTable() {
        try {
            DDL.createTable(TestHelper.TABLE + " (" +
                    "id INTEGER NOT NULL PRIMARY KEY," +
                    "surname text(30) NOT NULL," +
                    "score REAL)");
            assertTrue(TestHelper.tableExists());
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    private void fillTable(int count) throws SQLException {
        try (Connection conn = ConMan.getConnection();
             Statement st = conn.createStatement()) {
            conn.setAutoCommit(false);
            for (int i = 1; i <= count; i++) {
                st.addBatch(String.format("INSERT INTO %s (surname, score) VALUES ('%s', %d);%n", TestHelper.TABLE,
                        "test surname " + i, 4));
            }
            st.executeBatch();
            conn.commit();
        }
    }

    @Test
    @Order(2)
    void clearTable() {
        try {
            int countTestData = 500;
            fillTable(countTestData);
            boolean actual = countTestData == TestHelper.countTableData();
            DDL.clearTable(TestHelper.TABLE);
            actual &= 0 == TestHelper.countTableData();
            LOGGER.info("Clear from {}: {}.", countTestData, actual);
            assertTrue(actual);
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(3)
    void dropTable() {
        try {
            DDL.dropTable(TestHelper.TABLE);
            assertFalse(TestHelper.tableExists());
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @AfterAll
    void afterAll() {
        TestHelper.restoreTableFile();
    }
}
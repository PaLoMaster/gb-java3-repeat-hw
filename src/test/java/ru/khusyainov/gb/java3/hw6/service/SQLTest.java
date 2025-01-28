package ru.khusyainov.gb.java3.hw6.service;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;
import static ru.khusyainov.gb.java3.hw6.service.TestHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SQLTest {

    @BeforeAll
    void beforeAll() {
        renameTableFile();
        createTable();
        prepareExpectAndDataForBatchInsert();
    }

    @Test
    @Order(4)
    void update() {
        try {
            assertEquals(1, SQL.update(
                    "UPDATE " + TABLE + " SET score = 3.0 WHERE " + TEST_DATA_WHEREAS + ";"));
            assertEquals(TEST_DATA_SURNAME + ", 3.0", getTestDataFromTable(TEST_DATA_WHEREAS_SQL));
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(1)
    @Timeout(1)
    void insertBatch() {
        assertArrayEquals(EXPECT_BATCH, SQL.insert(TABLE, TABLE_COLUMNS, TEST_DATA_BATCH));
        try {
            assertEquals(TEST_DATA_ROW_BATCH.repeat(BATCH_COUNT),
                    getTestDataFromTable(" WHERE surname = '" + TEST_DATA_SURNAME_BATCH + "'"));
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(2)
    void insertOne() {
        try {
            assertArrayEquals(new int[]{1}, SQL.insert(TABLE, TABLE_COLUMNS, TEST_DATA_ROW));
            assertEquals(TEST_DATA_ROW, getTestDataFromTable(TEST_DATA_WHEREAS_SQL));
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(3)
    void select() {
        try {
            assertEquals(TEST_DATA_ROW,
                    getTestDataFromResultSet(DML.select(TABLE, TABLE_COLUMNS, TEST_DATA_WHEREAS_SQL)));
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @AfterAll
    void afterAll() {
        restoreTableFile();
    }
}
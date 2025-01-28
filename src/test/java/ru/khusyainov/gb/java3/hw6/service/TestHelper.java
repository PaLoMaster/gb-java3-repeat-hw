package ru.khusyainov.gb.java3.hw6.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;

import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;

public class TestHelper {
    private static final Logger LOGGER = LogManager.getLogger(TestHelper.class);
    private static final String DB_FILE = "homework6.db";
    private static final String DB_FILE_BAK = DB_FILE + ".bak";
    public static final String TABLE = "students";
    public static final String TABLE_COLUMNS = "surname, score";
    static final String TEST_DATA_SURNAME = "test data main";
    static final String TEST_DATA_SURNAME_BATCH = TEST_DATA_SURNAME + " batch";
    static final String TEST_DATA_ROW_BATCH = TEST_DATA_SURNAME_BATCH + ", 2.2";
    public static final int BATCH_COUNT = 500;
    static final int[] EXPECT_BATCH = new int[BATCH_COUNT];
    static final String[] TEST_DATA_BATCH = new String[BATCH_COUNT];
    static final String TEST_DATA_ROW = TEST_DATA_SURNAME + ", 2.2";
    static final String TEST_DATA_WHEREAS = "surname = '" + TEST_DATA_SURNAME + "'";
    static final String TEST_DATA_WHEREAS_SQL = " WHERE " + TEST_DATA_WHEREAS;

    private static void renameDB(String fileIn, String fileOut) {
        File out = new File(fileOut);
        LOGGER.info("Delete old/previous file  {}: {}.", fileOut, out.delete());
        LOGGER.info("Rename current db file   {} to file {}: {}.", fileIn, fileOut, new File(fileIn).renameTo(out));
    }

    public static void renameTableFile() {
        try {
            renameDB(DB_FILE, DB_FILE_BAK);
        } catch (Exception e) {
            logException(e);
        }
    }

    public static void restoreTableFile() {
        try {
            ConMan.closeConnection();
            renameDB(DB_FILE_BAK, DB_FILE);
        } catch (Exception e) {
            logException(e);
        }
    }

    public static boolean tableExists() throws SQLException {
        DatabaseMetaData meta = ConMan.getConnection().getMetaData();
        ResultSet resultSet = meta.getTables(null, null, TABLE, new String[]{"table"});
        return resultSet.next() || TABLE.equals(resultSet.getString("table_name"));
    }

    public static int countTableData() throws SQLException {
        try (Statement st = ConMan.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT count(*) FROM " + TestHelper.TABLE + ";")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public static void prepareExpectAndDataForBatchInsert() {
        Arrays.fill(TEST_DATA_BATCH, TEST_DATA_ROW_BATCH);
        Arrays.fill(EXPECT_BATCH, 1);
        LOGGER.info("Test data for batch insert: {}.", Arrays.toString(TEST_DATA_BATCH));
        LOGGER.info("Expect from batch insert: {}.", Arrays.toString(EXPECT_BATCH));
    }

    public static void createTable() {
        try (Connection conn = ConMan.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                    "id INTEGER NOT NULL PRIMARY KEY," +
                    "surname text(30) NOT NULL," +
                    "score REAL);");
        } catch (SQLException e) {
            logException(e);
        }
    }

    public static String getTestDataFromResultSet(ResultSet rs) throws SQLException {
        StringBuilder testData = new StringBuilder();
        while (rs.next()) {
            testData.append(rs.getString(1)).append(", ").append(rs.getFloat(2));
        }
        LOGGER.info("Test data from resultSet: {}.", testData);
        return testData.toString();
    }

    public static String getTestDataFromTable(String whereas) throws SQLException {
        try (Statement st = ConMan.getConnection().createStatement()) {
            return getTestDataFromResultSet(st.executeQuery("SELECT " + TABLE_COLUMNS + " FROM " +
                    TABLE + whereas + ";"));
        }
    }

    public static int getRandomTestId() {
        return (int) (1 + Math.random() * BATCH_COUNT);
    }

    public static String getTestCurrentTime() {
        return String.format("test %1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
    }
}
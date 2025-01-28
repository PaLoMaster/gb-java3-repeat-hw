package ru.khusyainov.gb.java3.hw6.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;

public class SQL {
    private static final Logger LOGGER = LogManager.getLogger(SQL.class);

    static int update(String sql) {
        int res = -1;
        try (Statement statement = ConMan.getConnection().createStatement()) {
            res = statement.executeUpdate(sql);
            LOGGER.info("Updates count: {}. SQL: {}", res, sql);
        } catch (SQLException e) {
            ErrorLogHelper.logException(e, sql);
        }
        return res;
    }

    static ResultSet select(String sql) {
        try {
            ResultSet rs = ConMan.getConnection().createStatement().executeQuery(sql);
            LOGGER.info("Is empty result set: {}. SQL: {}", !rs.isBeforeFirst(), sql);
            return rs;
        } catch (SQLException e) {
            ErrorLogHelper.logException(e, sql);
        }
        return null;
    }

    static int[] insert(String table, String insertColumns, String... valuesRow) {
        if (insertColumns == null || insertColumns.isEmpty()) {
            return null;
        }
        String valuesPrepared = "?,".repeat(insertColumns.split(",").length - 1) + "?";
        LOGGER.info("Batch insert valuesPrepared: {}.", valuesPrepared);
        Connection connection = null;
        int[] execute = null;
        try {
            connection = ConMan.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + table + " (" + insertColumns +
                    ") VALUES (" + valuesPrepared + "); ");
            for (String valueRow : valuesRow) {
                String[] valueRowParts = valueRow.split(",");
                for (int i = 1; i <= valueRowParts.length; i++) {
                    ps.setString(i, valueRowParts[i - 1]);
                }
                ps.addBatch();
            }
            execute = ps.executeBatch();
            connection.commit();
            LOGGER.info("Batch insert result: {}.", Arrays.toString(execute));
        } catch (SQLException e) {
            logException(e, table, insertColumns, Arrays.toString(valuesRow));
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                logException(e);
            }
        }
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logException(e);
        }
        return execute;
    }
}

package ru.khusyainov.gb.java3.hw6.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConMan {
    private static Connection conn;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            ErrorLogHelper.logException(e);
        }
    }

    private ConMan() {
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection("jdbc:sqlite:homework6.db");
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
        conn = null;
    }
}

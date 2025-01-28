package ru.khusyainov.gb.java3.hw6.service;

import java.sql.ResultSet;

public class DML {
    private DML() {
    }

    public static int[] insert(String table, String insertColumns, String... valuesRow) {
        return SQL.insert(table, insertColumns, valuesRow);
    }

    public static ResultSet select(String table, String what, String filterAndOther) {
        return SQL.select("SELECT " + what + " FROM " + table +
                (filterAndOther == null || filterAndOther.isEmpty() ? "" : " " + filterAndOther) + ";");
    }

    public static int update(String table, String whereas, String howChange) {
        return SQL.update("UPDATE " + table + " SET " + howChange + " WHERE " + whereas + ";");
    }

    public static int delete(String table, String whereas) {
        return SQL.update("DELETE FROM " + table + " WHERE " + whereas + ";");
    }
}

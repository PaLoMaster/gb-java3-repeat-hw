package ru.khusyainov.gb.java3.hw6.service;

public class DDL {
    private DDL() {
    }

    public static void createTable(String tableWithColumns) {
        SQL.update("CREATE TABLE IF NOT EXISTS " + tableWithColumns + ";");
    }

    public static void clearTable(String table) {
        SQL.update("DELETE FROM " + table + ";" + "REINDEX " + table + ";");
    }

    public static void dropTable(String table) {
        SQL.update("DROP TABLE IF EXISTS '" + table + "';");
    }
}

package ru.khusyainov.gb.java3.hw2;

import java.sql.*;
import java.util.Scanner;

public class HomeWork2 {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        try {
            connect();
            createTable();
            clearTable();
            insert();
            shop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:homework2.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable() throws SQLException {
        stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS products (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 prodid TEXT NOT NULL,
                 title TEXT NOT NULL,
                 cost INTEGER NOT NULL
                 );""");
    }

    private static void clearTable() throws SQLException {
        stmt.executeUpdate("DELETE FROM products;");
    }

    private static void insert() throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 1; i <= 10_000; i++) {
            stmt.addBatch(String.format("INSERT INTO products (prodid, title, cost) VALUES ('%s', '%s', %d)",
                    "id_товара " + i, "товар" + i, i * 10));
        }
        stmt.executeBatch();
        connection.commit();
    }

    enum Command {
        PRICE("/цена"), CHANGE_COST("/сменитьцену"), PRODUCTS("/товарыпоцене"), EXIT("/выход");
        final String command;

        Command(String command) {
            this.command = command;
        }

        static Command parse(String command) {
            for (Command value : values()) {
                if (value.command.equals(command)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Ошибка определения команды");
        }
    }

    private static void shop() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Command command;
        String[] inputSplit;
        System.out.println("Доступные команды:\n" +
                Command.PRICE.command + " title - узнать цену товара title;\n" +
                Command.CHANGE_COST.command + " title cost - сменить цену товара title на cost;\n" +
                Command.PRODUCTS.command + " low high - вывести товары в заданном ценовом диапазоне;\n" +
                Command.EXIT.command + " - выход из программы.");
        while (true) {
            input = scanner.nextLine();
            inputSplit = input.split("\\s");
            try {
                command = Command.parse(inputSplit[0]);
                if (command == Command.EXIT) {
                    break;
                }
                switch (command) {
                    case PRICE -> showPrice(inputSplit[1]);
                    case CHANGE_COST -> updatePrice(inputSplit[1], Integer.parseInt(inputSplit[2]));
                    case PRODUCTS -> showByPrice(Integer.parseInt(inputSplit[1]), Integer.parseInt(inputSplit[2]));
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.err.println("Ошибка ввода команды.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static void showProducts(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " '" + rs.getString("prodid") + "' '" +
                    rs.getString("title") + "' " + rs.getInt(4));
        }
    }

    private static void showPrice(String title) throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE title = '" + title + "';")) {
            if (!rs.isBeforeFirst()) {
                System.out.println("Товар с именем '" + title + "' не найден.");
            } else {
                showProducts(rs);
            }
        }
    }

    private static void updatePrice(String title, int cost) throws SQLException {
        System.out.println("До изменения:");
        showPrice(title);
        int count = stmt.executeUpdate("UPDATE products SET cost = " + cost + " WHERE title= '" + title + "';");
        if (count > 0) {
            System.out.println("После изменения:");
            showPrice(title);
        }
    }

    private static void showByPrice(int low, int high) throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE cost >= " + low + " AND cost <= " + high + ";")) {
            if (!rs.isBeforeFirst()) {
                System.out.println("Товары с ценами от " + low + " до " + high + " не найдены.");
            } else {
                showProducts(rs);
            }
        }
    }
}

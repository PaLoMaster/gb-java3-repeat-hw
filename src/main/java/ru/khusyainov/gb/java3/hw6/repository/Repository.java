package ru.khusyainov.gb.java3.hw6.repository;

import java.util.List;

public interface Repository<T> {
    void createRepository();

    void clearRepository();

    void deleteRepository();

    boolean save(T item);

    boolean update(T item);

    T get(int id);

    T delete(int id);

    boolean delete(T item);

    List<T> getAll();
}

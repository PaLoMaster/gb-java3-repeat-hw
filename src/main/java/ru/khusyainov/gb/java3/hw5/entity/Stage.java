package ru.khusyainov.gb.java3.hw5.entity;

public abstract class Stage {
    protected int length;
    protected String description;

    public abstract void go(Car car);

    @Override
    public String toString() {
        return description;
    }
}

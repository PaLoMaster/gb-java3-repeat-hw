package ru.khusyainov.gb.java3.hw4.entity;

public abstract class Document {
    private final String name;
    private final int pages;
    public Document(String name, int pages) {
        this.name = name;
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                '}';
    }
}

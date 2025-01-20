package ru.khusyainov.gb.java3.hw4.device;

import ru.khusyainov.gb.java3.hw4.entity.Document;

public class Printer extends Device {
    public Printer() {
        this("Печать");
    }

    public Printer(String task) {
        super(task);
    }

    public void print(Document document) {
        start(document);
    }
}

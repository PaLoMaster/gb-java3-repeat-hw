package ru.khusyainov.gb.java3.hw4.device;

import ru.khusyainov.gb.java3.hw4.entity.Document;

public class Scanner extends Device {
    public Scanner() {
        this("Сканирование");
    }

    public Scanner(String task) {
        super(task);
    }

    public void scan(Document document) {
        start(document);
    }
}

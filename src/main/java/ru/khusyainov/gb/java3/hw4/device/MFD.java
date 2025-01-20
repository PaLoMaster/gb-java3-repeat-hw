package ru.khusyainov.gb.java3.hw4.device;

import ru.khusyainov.gb.java3.hw4.entity.Document;

public class MFD extends Device {
    private final Printer printer;
    private final Scanner scanner;
    public MFD() {
        this("МФУ");
    }

    public MFD(String task) {
        super(task);
        printer = new Printer();
        scanner = new Scanner();
    }

    @Override
    public void start(Document document) {
        print(document);
    }

    public void print(Document document) {
        printer.print(document);
    }

    public void scan(Document document) {
        scanner.scan(document);
    }
}

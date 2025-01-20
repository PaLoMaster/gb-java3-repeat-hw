package ru.khusyainov.gb.java3.hw4.device;

import ru.khusyainov.gb.java3.hw4.entity.Document;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public abstract class Device {
    final String task;
    final Queue<Document> queue;

    public Device(String task) {
        this.task = task;
        queue = new LinkedList<>();
    }

    public void start(Document document) {
        queue.add(document);
        new Thread(() -> {
            synchronized (queue) {
                try {
                    start(task, Objects.requireNonNull(queue.poll()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void start(String toDo, Document document) throws InterruptedException {
        String processName = Thread.currentThread().getName(), docString = document.toString(),
                toDoLower = toDo.toLowerCase();
        System.out.printf("Поступил документ из процесса %9s (%s): %s.%n", processName, toDoLower, docString);
        for (int i = 1; i <= document.getPages(); i++) {
            System.out.printf("%-13s %2d страниц(ы) документа %s.%n", toDo, i, docString);
            Thread.sleep(50);
        }
        System.out.printf("Обработан документ процессом  %9s (%s): %s.%n", processName, toDoLower, docString);
    }
}

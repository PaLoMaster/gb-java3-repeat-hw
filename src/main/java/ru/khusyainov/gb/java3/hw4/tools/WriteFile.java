package ru.khusyainov.gb.java3.hw4.tools;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class WriteFile {
    private final String fileName;
    private final int threadCount;
    private final Thread[] threads;
    private BufferedOutputStream bos;

    public WriteFile(String fileName, int threadCount) {
        this.fileName = fileName;
        this.threadCount = threadCount;
        this.threads = new Thread[threadCount];
    }

    private String createData(int unicode) {
        int repeater = 100;
        StringBuilder data = new StringBuilder(repeater * 4 + 30).append(Thread.currentThread().getName()).append(" ");
        for (int i = 0; i < repeater; i++) {
            data.append(Character.toChars(unicode)).append(" ");
        }
        return data.append("запись ").toString();
    }

    public void write() {
        int records = 10;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(fileName));
            for (int i = 0; i < threadCount; i++) {
                int I = i;
                threads[i] = new Thread(() -> {
                    String data = createData('А' + I);
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            synchronized (fileName) {
                                if (bos != null && !Thread.currentThread().isInterrupted()) {
                                    for (int j = 0; j < records; j++) {
                                        bos.write((data + j + ".\n").getBytes());
                                        try {
                                            Thread.sleep(20);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.printf("\n\nПоток %10s записи в файл завершён.\n\n", Thread.currentThread().getName());
                });
                threads[i].start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void interrupt() {
        Arrays.stream(threads).forEach(Thread::interrupt);
        if (bos != null) {
            synchronized (fileName) {
                try {
                    bos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
        }
    }
}

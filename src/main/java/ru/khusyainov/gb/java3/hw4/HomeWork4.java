package ru.khusyainov.gb.java3.hw4;

import ru.khusyainov.gb.java3.hw4.device.MFD;
import ru.khusyainov.gb.java3.hw4.entity.DocumentImpl;
import ru.khusyainov.gb.java3.hw4.tools.ArrayRepeat;
import ru.khusyainov.gb.java3.hw4.tools.WriteFile;

import java.util.Random;

public class HomeWork4 {
    public static void main(String[] args) {
        System.out.println("ABC...");
        ArrayRepeat arrayRepeat = new ArrayRepeat(new char[]{'A', 'B', 'C'});
        for (int i = 0; i < arrayRepeat.size(); i++) {
            final int I = i;
            new Thread(() -> arrayRepeat.repeat(I)).start();
        }
        while (arrayRepeat.isNotFinished()) {
            arrayRepeat.isNotFinished(); // не захотел join'(ы), а это - заткнуть "цикл без тела")
        }
        System.out.println("\n\nЗапись в файл начата...");
        WriteFile writeFile = new WriteFile("hw4-multi-write.txt", 3);
        new Thread(writeFile::write).start();
        System.out.println("\n\nОтправка заданий на МФУ (параллельно записи в файл)...");
        MFD mfd = new MFD();
        Random random = new Random();
        int maxPages = 31;
        for (int i = 1; i <= 10; i++) {
            mfd.print(new DocumentImpl("PrintDoc " + i, random.nextInt(1, maxPages)));
            mfd.scan(new DocumentImpl("ScanDoc " + i, random.nextInt(1, maxPages)));
        }
        int pause = 10;
        System.out.println("\n\nОтправка заданий на МФУ закончена. Ждём " + pause + " сек.");
        try {
            Thread.sleep(pause * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeFile.interrupt();
        System.out.println("\n\nЗапись в файл прервана.");
    }
}

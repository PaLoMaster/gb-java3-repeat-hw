package ru.khusyainov.gb.java3.hw3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class HomeWork3 {
    private static final String FILES = "hw3-files/";
    private static final String BIG_FILE_NAME = FILES + "file1-5.txt";

    public static void main(String[] args) {
        readFileToByteArray(FILES + "file50b0.txt");
        readFileToByteArray(FILES + "file50b.txt");
        writeFilesToOne(new String[]{FILES + "file10.txt", FILES + "file20.txt", FILES + "file30.txt",
                FILES + "file40.txt", FILES + "file50.txt"});
        writeFilesToOne(new String[]{FILES + "file1.txt", FILES + "file2.txt", FILES + "file3.txt",
                FILES + "file4.txt", FILES + "file5.txt"});
        bigFileMaking();
        readBySheets();
    }

    private static void readFileToByteArray(String fileName) {
        byte[] bytes = new byte[50];
        int readCount;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            if ((readCount = fis.read(bytes)) != -1) {
                System.out.println("Из " + fileName + " прочитано " + readCount + " байт:\n" +
                        Arrays.toString(Arrays.copyOf(bytes, readCount)) + "\n");
            } else {
                System.out.println(fileName + " пуст, ничего не прочитано.\n");
            }
        } catch (IOException e) {
            System.out.println(fileName + " не найден, ничего не прочитано, или ошибка чтения." + e + "\n");
        }
    }

    private static void writeFilesToOne(String[] fileNames) {
        List<InputStream> lis = new java.util.ArrayList<>();
        SequenceInputStream sis = null;
        FileOutputStream fos = null;
        try {
            for (String file : fileNames) {
                lis.add(new FileInputStream(file));
            }
            sis = new SequenceInputStream(Collections.enumeration(lis));
            fos = new FileOutputStream(BIG_FILE_NAME);
            int read;
            while ((read = sis.read()) != -1) {
                fos.write(read);
            }
            if (fos.getChannel().size() == 0) {
                System.out.println("Файл(ы) пусты, ничего не прочитано/переписано:\n" + Arrays.toString(fileNames) + "\n");
            } else {
                System.out.println("Файл(ы) успешно переписаны в файл " + BIG_FILE_NAME + ":\n" + Arrays.toString(fileNames) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Файл(ы) не найден(ы), ничего не прочитано, или ошибка чтения/записи:\n" +
                    Arrays.toString(fileNames) + "\n" + e + "\n");
        } finally {
            if (sis != null) {
                try {
                    sis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void bigFileMaking() {
        int needSize = 11 * 1024 * 1024;
        try (FileInputStream fis = new FileInputStream(BIG_FILE_NAME);
             BufferedInputStream in = new BufferedInputStream(fis);
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(BIG_FILE_NAME, true))) {
            long time = System.currentTimeMillis(), fileSize, count = 0;
            while ((fileSize = fis.getChannel().size()) < needSize) {
                out.write(in.read());
                if (++count == fileSize) {
                    out.flush();
                }
            }
            time -= System.currentTimeMillis();
            System.out.println("Создан файл " + BIG_FILE_NAME + " размером " + fis.getChannel().size() + " за " +
                    (-time / 1_000) + "сек (для следующего задания).\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readBySheets() {
        Scanner scanner = new Scanner(System.in);
        int input;
        RandomAccessFile raf = null;
        int pageSize = 1800, readCount, pages;
        long time, timeToRead = 5_000;
        try {
            raf = new RandomAccessFile(BIG_FILE_NAME, "r");
            pages = (int) Math.ceil(1.0 * raf.getChannel().size() / pageSize);
            System.out.println("Доступно " + pages + " страниц.\n" +
                    "Введите номер страницы для вывода или 0 для завершения программы.\n");
            byte[] page = new byte[pageSize];
            while ((input = scanner.nextInt()) != 0) {
                readCount = 0;
                time = System.currentTimeMillis();
                try {
                    raf.seek((input - 1L) * pageSize);
                    while (readCount < pageSize) {
                        int temp = raf.read();
                        if (temp != -1) {
                            page[readCount++] = (byte) temp;
                        } else {
                            throw new EOFException();
                        }
                        if ((time - System.currentTimeMillis()) > timeToRead) {
                            throw new TimeoutException();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка определения введённого номера страницы.\n");
                } catch (EOFException e) {
                    if (readCount > 0) {
                        System.out.println("Это заключительная страница:\n");
                    } else {
                        System.out.println("Нет такой страницы. Их " + pages + ".\n");
                    }
                } catch (TimeoutException e) {
                    System.out.println("Превышено время считывания страницы (возможно, она считана не полностью).\n");
                } catch (IOException e) {
                    System.out.println("Ошибка чтения...\n");
                }
                time -= System.currentTimeMillis();
                if (readCount > 0) {
                    System.out.println(new String(page, 0, readCount, StandardCharsets.UTF_8) +
                            "\nСтраница считана за " + (-time / 1_000) + "сек.\n");
                }
            }
        } catch (IOException e) {
            System.out.println(BIG_FILE_NAME + " не найден, ничего не прочитано, или ошибка чтения." + e + "\n");
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package ru.khusyainov.gb.java3.hw4.tools;

public class ArrayRepeat {
    private final Object monitor = new Object();
    private int currentIndex = 0;
    private boolean finished = false;

    public ArrayRepeat(char[] array) {
        this.array = array;
    }

    private final char[] array;

    public void repeat(int index) {
        int repeatCount = 5;
        synchronized (monitor) {
            try {
                for (int i = 0; i < repeatCount; i++) {
                    while (currentIndex != index) {
                        monitor.wait();
                    }
                    System.out.print(array[index]);
                    currentIndex = index + 1 == array.length ? 0 : index + 1;
                    monitor.notifyAll();
                }
                if (index + 1 == array.length) {
                    System.out.println();
                    finished = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        return array.length;
    }

    public boolean isNotFinished() {
        return !finished;
    }
}

package ru.khusyainov.gb.java3.hw5.entity;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore limit;

    public Tunnel(int carsCount) {
        length = 80;
        int limitCount = carsCount / 2;
        description = "Тоннель " + length + " метров с ограничением в проезде " + limitCount + " участникам";
        limit = new Semaphore(limitCount);
    }

    @Override
    public void go(Car car) {
        try {
            try {
                System.out.println(car.getName() + " готовится к этапу(ждет): " + this);
                limit.acquire();
                System.out.println(car.getName() + " начал этап: " + this);
                Thread.sleep(1000L * length / car.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(car.getName() + " закончил этап: " + this);
                limit.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

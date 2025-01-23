package ru.khusyainov.gb.java3.hw5;

import ru.khusyainov.gb.java3.hw5.entity.Car;
import ru.khusyainov.gb.java3.hw5.entity.Road;
import ru.khusyainov.gb.java3.hw5.entity.Stage;
import ru.khusyainov.gb.java3.hw5.entity.Tunnel;

public class HomeWork5 {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(CARS_COUNT, new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        System.out.println("Трасса состоит из " + race.getStages().size() + " частей:");
        for (Stage stage : race.getStages()) {
            System.out.println("\t" + stage);
        }
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            new Thread(cars[i]).start();
        }
        try {
            race.start();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            race.end();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

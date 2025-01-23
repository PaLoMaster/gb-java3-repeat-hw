package ru.khusyainov.gb.java3.hw5;

import ru.khusyainov.gb.java3.hw5.entity.Car;
import ru.khusyainov.gb.java3.hw5.entity.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Race {
    private final ArrayList<Stage> stages;
    private final CyclicBarrier REGISTRY;
    private final Object END = new Object();
    private boolean hasNotWinner = true;

    public Race(int carsCount, Stage... stages) {
        REGISTRY = new CyclicBarrier(carsCount + 1);
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public void start() throws BrokenBarrierException, InterruptedException {
        REGISTRY.await();
    }

    public void end(Car car) throws BrokenBarrierException, InterruptedException {
        synchronized (END) {
            if (hasNotWinner) {
                hasNotWinner = false;
                System.out.println(car.getName() + " - WIN");
            }
        }
        REGISTRY.await();
    }

    public void end() throws BrokenBarrierException, InterruptedException {
        REGISTRY.await();
    }
}

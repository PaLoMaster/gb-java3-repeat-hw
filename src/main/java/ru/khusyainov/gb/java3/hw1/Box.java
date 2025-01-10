package ru.khusyainov.gb.java3.hw1;

import ru.khusyainov.gb.java3.hw1.fruits.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Box<F extends Fruit> {
    private final ArrayList<F> box;
    private final String type;

    public Box(int initialCapacity, Class<F> fruitsClass) {
        box = new ArrayList<>(initialCapacity);
        type = fruitsClass.getSimpleName();
    }

    public void add(List<F> fruits) {
        box.addAll(fruits);
    }

    @SafeVarargs
    public final void add(F... fruit) {
        add(List.of(fruit));
    }

    public void pourOver(Box<F> toBox) {
        toBox.add(box);
        box.clear();
    }

    public float getWeight() {
        return box.isEmpty() ? 0 : box.size() * box.get(0).getWeight();
    }

    public int getSize() {
        return box.size();
    }

    public boolean compare(Box<?> otherBox) {
        return Objects.equals(getWeight(), otherBox.getWeight());
    }

    public String getContentType() {
        return type;
    }

    @Override
    public String toString() {
        return "В коробке " + getContentType() + "s: " + getSize() + " и коробка весит " + getWeight() + ".";
    }
}

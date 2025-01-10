package ru.khusyainov.gb.java3.hw1;

import ru.khusyainov.gb.java3.hw1.fruits.Apple;
import ru.khusyainov.gb.java3.hw1.fruits.Orange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomeWork1 {
    public static void main(String[] args) {
        System.out.println("Меняем два элемента массива местами.\n\nМассив строк");
        String[] strings = {"Один__", "Два___", "Три___", "Четыре", "Пять__", "Шесть_"};
        System.out.println(getChangeElementsResult(strings, 2, 4));
        Integer[] ints = {1, 2, 3, 4, 5, 6};
        System.out.println(getChangeElementsResult(ints, 0, 3));
        System.out.println("\nПреобразуем массив 1 в ArrayList: " + arrayToArrayList(strings));
        System.out.println("\nПреобразуем массив 2 в ArrayList: " + arrayToArrayList(ints));
        Box<Apple> box1, box2;
        Box<Orange> box3, box4;
        Random random = new Random();
        int maxFruitsCount = 50;
        System.out.println("\n\nПополняем коробки...");
        int fruitsCount = random.nextInt(maxFruitsCount);
        box1 = new Box<>(fruitsCount, Apple.class);
        box1.add(getNewApples(fruitsCount));
        fruitsCount = random.nextInt(maxFruitsCount) * 3;
        box2 = new Box<>(fruitsCount, Apple.class);
        box2.add(getNewApples(fruitsCount));
        fruitsCount = random.nextInt(maxFruitsCount);
        box3 = new Box<>(fruitsCount, Orange.class);
        box3.add(getNewOranges(random.nextInt(maxFruitsCount)));
        fruitsCount = (int) (box2.getSize() / 1.5f);
        box4 = new Box<>(fruitsCount, Orange.class);
        box4.add(getNewOranges(fruitsCount));
        System.out.println(getBoxDescriptions(box1, box2, box3, box4));
        System.out.println("Добавляем яблоки в коробку 4 - невозможно (ошибка компиляции).");
//        box4.add(getNewApples(fruitsCount));
        System.out.println("\nСравниваем коробки...");
        System.out.println(getBoxCompareResult(box1, box2, box3, box4));
        System.out.println("Пересыпаем коробку 2 в коробку 1...");
        box2.pourOver(box1);
        System.out.println(getBoxDescriptions(box1, box2));
        System.out.println("Повторно пересыпаем коробку 2 в коробку 1 - без изменений.");
        box2.pourOver(box1);
        System.out.println(getBoxDescriptions(box1, box2));
        System.out.println("Пересыпаем коробку 3 в коробку 1 - невозможно (ошибка компиляции).");
//        box3.pourOver(box1);
        System.out.println("Пересыпаем коробку 4 в коробку 2 - невозможно (ошибка компиляции).");
//        box4.pourOver(box2);
        System.out.println("\nПересыпаем коробку 4 в коробку 3...");
        box4.pourOver(box3);
        System.out.println(getBoxDescriptions(box1, box2, box3, box4));
    }

    public static <T> void changeElements(T[] array, int firstIndex, int secondIndex) {
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    public static <T> String getChangeElementsResult(T[] array, int firstIndex, int secondIndex) {
        StringBuilder result = new StringBuilder("До смены:    ").append(Arrays.toString(array));
        result.append("\nМеняем местами элементы ").append(firstIndex + 1).append(" и ").append(secondIndex + 1);
        changeElements(array, firstIndex, secondIndex);
        result.append("\nПосле смены: ").append(Arrays.toString(array)).append("\n");
        return result.toString();
    }

    public static <T> ArrayList<T> arrayToArrayList(T[] array) {
        return new ArrayList<>(List.of(array));
    }

    public static Apple[] getNewApples(int count) {
        Apple[] apples = new Apple[count];
        Arrays.fill(apples, new Apple());
        return apples;
    }

    public static Orange[] getNewOranges(int count) {
        Orange[] oranges = new Orange[count];
        Arrays.fill(oranges, new Orange());
        return oranges;
    }

    private static String getBoxDescriptions(Box<?>... boxes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < boxes.length; i++) {
            result.append(i + 1).append(". ").append(boxes[i]).append("\n");
        }
        return result.toString();
    }

    private static String getBoxCompareResult(Box<?>... boxes) {
        StringBuilder result = new StringBuilder();
        int preLength = boxes.length - 1;
        for (int i = 0; i < preLength; i++) {
            for (int j = i + 1; j < boxes.length; j++) {
                result.append("Сравним коробки ").append(i + 1).append(" и ").append(j + 1).append(": ")
                        .append(boxes[i].compare(boxes[j]) ? "" : "не ").append("равны.\n");
            }
        }
        return result.toString();
    }
}

package ru.khusyainov.gb.java3.hw6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.khusyainov.gb.java3.hw6.entity.Student;
import ru.khusyainov.gb.java3.hw6.repository.StudentRepository;
import ru.khusyainov.gb.java3.hw6.service.ConMan;
import ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper;

import java.sql.SQLException;
import java.util.Arrays;

public class HomeWork6 {
    private static final Logger LOGGER = LogManager.getLogger(HomeWork6.class);

    public static void main(String[] args) {
        System.out.println("Array part after the last four:");
        printArrayPartAfterLastFourResult(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4});
        printArrayPartAfterLastFourResult(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7});
        printArrayPartAfterLastFourResult(new int[]{1, 2, 4, 2, 3, 1, 7});
        printArrayPartAfterLastFourResult(new int[]{1, 2, 2, 3, 1, 7});
        printArrayPartAfterLastFourResult(new int[0]);
        System.out.println("\nIs array of one and four:");
        printIsArrayOnlyOfOneAndFourResult(new int[]{1, 1, 4, 4, 1, 1, 4, 1, 4, 4});
        printIsArrayOnlyOfOneAndFourResult(new int[]{0, 2, 5, 0, 2, 3, 3, 6, 7, 6});
        printIsArrayOnlyOfOneAndFourResult(new int[]{1, 4});
        printIsArrayOnlyOfOneAndFourResult(new int[]{4});
        printIsArrayOnlyOfOneAndFourResult(new int[]{1});
        printIsArrayOnlyOfOneAndFourResult(new int[0]);
        System.out.println("\n\nDatabase manipulations:");
        StudentRepository repository = new StudentRepository();
        System.out.println("Creating table...");
        repository.createRepository();
        System.out.printf("Table content: %d students.%n", repository.countAll());
        final int COUNT = 100;
        System.out.println("Fill in by random data...");
        repository.fillRepositoryByRandomData(COUNT);
        System.out.println("Table content count: " + repository.countAll());
        System.out.println("Table content: " + repository.getAll());
        final int RANDOM_COUNT = 10;
        System.out.printf("%nRandom %d students and update/delete:%n", RANDOM_COUNT);
        for (int i = 0, j; i < RANDOM_COUNT; i++) {
            j = (int) (1 + Math.random() * COUNT);
            Student student = repository.get(j);
            System.out.printf("Get random by id %3d:%n\t\t%s%n", j, student);
            if (student != null) {
                student.setScore((int) (Math.random() * 20 + 30) / 10f);
                boolean update = repository.update(student);
                System.out.println(update ? "\tWith updated score:\n\t\t" + student
                        : "\tSomething gone wrong while updating.");
                student.setSurname("Updated surname " + (int) (1 + Math.random() * COUNT));
                update = repository.update(student);
                System.out.println(update ? "\tWith updated surname:\n\t\t" + student
                        : "\tSomething gone wrong while updating.");
                System.out.println("\tDelete him: " + repository.delete(student));
            }
            j = (int) (1 + Math.random() * COUNT);
            System.out.printf("Delete random student by id %d:%n\t%s%n", j, repository.delete(j));
        }
        System.out.printf("%nTable content: %d students.%n", repository.countAll());
        System.out.printf("Adding new random %d students:%n", RANDOM_COUNT);
        for (int i = 1; i <= RANDOM_COUNT; i++) {
            Student student = new Student("New random surname " + i, (int) (Math.random() * 20 + 30) / 10f);
            boolean save = repository.save(student);
            System.out.println(save ? student : "Something gone wrong while adding.");
        }
        System.out.printf("Table content: %d students.%n", repository.countAll());
        System.out.println("\nClearing table...");
        repository.clearRepository();
        System.out.printf("Table content: %d students.%n", repository.countAll());
        System.out.println("Table delete...");
        repository.deleteRepository();
        try {
            ConMan.closeConnection();
        } catch (SQLException e) {
            ErrorLogHelper.logException(e);
        }
    }

    static int[] getArrayPartAfterLastFour(int[] from) {
        LOGGER.info("Array: {}.", Arrays.toString(from));
        for (int i = from.length; i > 0; i--) {
            if (from[i - 1] == 4) {
                LOGGER.info("Index of 4: {}.", i - 1);
                return Arrays.copyOfRange(from, i, from.length);
            }
        }
        String error = "There are not found 4 in array.";
        LOGGER.info(error);
        throw new RuntimeException(error);
    }

    private static void printArrayPartAfterLastFourResult(int[] from) {
        System.out.printf("Array in : %s%n", Arrays.toString(from));
        try {
            System.out.printf("Array out: %s%n", Arrays.toString(getArrayPartAfterLastFour(from)));
        } catch (RuntimeException e) {
            System.out.printf("Error: %s%n", e);
        }
    }

    static boolean isArrayOnlyOfOneAndFour(int[] from) {
        int one = Arrays.stream(from).filter(i -> i == 1).toArray().length;
        int four = Arrays.stream(from).filter(i -> i == 4).toArray().length;
        boolean result = one > 0 && four > 0 && one + four == from.length;
        LOGGER.info("Array is {} only of one and four: {}.", (result ? "" : "not"), Arrays.toString(from));
        return result;
    }

    private static void printIsArrayOnlyOfOneAndFourResult(int[] from) {
        System.out.printf("Array in : %s%n", Arrays.toString(from));
        System.out.printf("Array is only of one and four: %b%n", isArrayOnlyOfOneAndFour(from));
    }
}

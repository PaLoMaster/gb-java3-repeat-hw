package ru.khusyainov.gb.java3.hw6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HomeWork6Test {
    private static Stream<Arguments> dataForArrayPartAfterLastFour() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4}, new int[0]));
        out.add(Arguments.arguments(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}));
        out.add(Arguments.arguments(new int[]{1, 2, 4, 2, 3, 1, 7}, new int[]{2, 3, 1, 7}));
        return out.stream();
    }

    @MethodSource("dataForArrayPartAfterLastFour")
    @ParameterizedTest
    void getArrayPartAfterLastFour(int[] from, int[] result) {
        assertArrayEquals(result, HomeWork6.getArrayPartAfterLastFour(from));
    }

    private int[] arrayFromString(String arrayString) {
        return Arrays.stream(arrayString.split(",")).mapToInt(s -> Integer.parseInt(s.trim())).toArray();
    }

    @CsvSource({
            "'1, 2, 2, 3, 1, 7'",
            "'1, 2, 2, 3'",
            "'1, 2'"
    })
    @ParameterizedTest
    void getArrayPartAfterLastFourException(String arrayString) {
        assertThrows(RuntimeException.class, () -> HomeWork6.getArrayPartAfterLastFour(arrayFromString(arrayString)));
    }

    @Test
    void getArrayPartAfterLastFourEmptyException() {
        assertThrows(RuntimeException.class, () -> HomeWork6.getArrayPartAfterLastFour(new int[0]));
    }

    @CsvSource({
            "'1, 1, 4, 4, 1, 1, 4, 1, 4, 4'",
            "'4, 4, 4, 4, 1, 4, 4, 4, 4, 4'",
            "'1, 1, 1, 1, 1, 1, 1, 1, 1, 4'",
            "'1, 4, 4'",
            "'4, 1, 4'",
            "'1, 4'",
            "'4, 1'"
    })
    @ParameterizedTest
    void isArrayOnlyOfOneAndFourTrue(String arrayString) {
        assertTrue(HomeWork6.isArrayOnlyOfOneAndFour(arrayFromString(arrayString)));
    }

    @CsvSource({
            "'0, 2, 5, 0, 2, 3, 3, 6, 7, 6'",
            "'3, 3, 6, 7, 6'",
            "'1'",
            "'4'"
    })
    @ParameterizedTest
    void isArrayOnlyOfOneAndFourFalse(String arrayString) {
        assertFalse(HomeWork6.isArrayOnlyOfOneAndFour(arrayFromString(arrayString)));
    }

    @Test
    void isArrayOnlyOfOneAndFourEmpty() {
        assertFalse(HomeWork6.isArrayOnlyOfOneAndFour(new int[0]));
    }
}

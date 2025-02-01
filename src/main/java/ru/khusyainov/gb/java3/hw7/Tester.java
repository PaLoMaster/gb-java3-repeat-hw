package ru.khusyainov.gb.java3.hw7;

import ru.khusyainov.gb.java3.hw7.anotation.AfterSuite;
import ru.khusyainov.gb.java3.hw7.anotation.BeforeSuite;
import ru.khusyainov.gb.java3.hw7.anotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Tester {
    public static void start(String clazz) {
        try {
            start(Class.forName(clazz));
        } catch (ClassNotFoundException e) {
            System.out.printf("Failed to find %s class.%n", clazz);
        }
    }

    private static <A> Method getIfFirst(Method previous, Method current, Class<A> annotation) {
        if (previous == null) {
            return current;
        }
        throw new RuntimeException(String.format("Can't invoke methods of class %s...%n@%s method can be only one, " +
                        "but it's second at least... 1st - %s, 2nd - %s.", previous.getDeclaringClass().getName(),
                annotation.getSimpleName(), previous.getName(), current.getName()));
    }

    private static void invokeMethod(Method method) {
        try {
            Object instance = method.getDeclaringClass().getConstructor().newInstance();
            System.out.printf("%nInvoke method %s of instance %s with annotation(s) %s", method.getName(),
                    instance.getClass().getSimpleName(), Arrays.stream(method.getAnnotations())
                            .map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining(", ")));
            if (method.getAnnotation(Test.class) != null) {
                System.out.printf(" by priority value %d.%n", method.getAnnotation(Test.class).priority());
            } else {
                System.out.printf(".%n");
            }
            method.setAccessible(true);
            method.invoke(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            System.err.printf("Error while @%s invoke of method %s:%n%s.%n%s%n", Arrays.stream(method.getAnnotations())
                            .map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining(", ")),
                    method.getName(), e.getMessage(), Arrays.stream(e.getStackTrace()).map(Object::toString)
                            .collect(Collectors.joining("\n\t")));
        }
    }

    public static <T> void start(Class<T> clazz) {
        Method before = null;
        Method after = null;
        Map<Integer, LinkedList<Method>> tests = new TreeMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (method.isAnnotationPresent(Test.class)) {
                    tests.computeIfAbsent(((Test) annotation).priority(), k -> new LinkedList<>()).add(method);
                } else if (method.isAnnotationPresent(BeforeSuite.class)) {
                    before = getIfFirst(before, method, BeforeSuite.class);
                } else if (method.isAnnotationPresent(AfterSuite.class)) {
                    after = getIfFirst(after, method, AfterSuite.class);
                }
            }
        }
        if (before != null) {
            invokeMethod(before);
        }
        tests.forEach((priority, methods) -> methods.forEach(Tester::invokeMethod));
        if (after != null) {
            invokeMethod(after);
        }
    }
}

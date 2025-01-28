package ru.khusyainov.gb.java3.hw6.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class ErrorLogHelper {
    private static final Logger LOGGER = LogManager.getLogger(ErrorLogHelper.class);
    public static void logException(Exception e, String... data) {
        Arrays.stream(data).forEach(LOGGER::error);
        LOGGER.error(e.getMessage());
        Arrays.stream(e.getStackTrace()).forEach(LOGGER::error);
    }

}

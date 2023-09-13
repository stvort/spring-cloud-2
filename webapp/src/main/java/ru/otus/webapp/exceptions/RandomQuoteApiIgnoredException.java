package ru.otus.webapp.exceptions;

public class RandomQuoteApiIgnoredException extends RuntimeException {
    public RandomQuoteApiIgnoredException(String message) {
        super(message);
    }

    public RandomQuoteApiIgnoredException(String message, Throwable cause) {
        super(message, cause);
    }
}

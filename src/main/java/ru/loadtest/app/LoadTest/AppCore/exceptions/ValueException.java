package ru.loadtest.app.LoadTest.AppCore.exceptions;

public class ValueException extends Exception {
    private int number;
    public int getNumber() {
        return number;
    }
    public ValueException(String message, int number) {
        super(message);
        this.number = number;
    }
}

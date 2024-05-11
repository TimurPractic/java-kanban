package ru.yandex.practicum.tasktracker.exception;

public class ManagerIOException extends RuntimeException {
    public ManagerIOException(Exception cause) {
        super(cause);
    }
}

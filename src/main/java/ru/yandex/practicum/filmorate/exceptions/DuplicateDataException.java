package ru.yandex.practicum.filmorate.exceptions;

public class DuplicateDataException extends ru.yandex.practicum.filmorate.exceptions.ValidationException {
    public DuplicateDataException(String message) {
        super(message);
    }
}

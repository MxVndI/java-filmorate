package ru.yandex.practicum.filmorate.exceptions;

public class ConditionsNotMetException extends ru.yandex.practicum.filmorate.exceptions.ValidationException {
    public ConditionsNotMetException(String message) {
        super(message);
    }
}

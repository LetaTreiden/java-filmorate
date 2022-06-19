package ru.yandex.practicum.filmorate.exceptionHandler;

import lombok.Getter;

@Getter
public class Error {
    private final String error;
    private final String description;

    public Error(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
package ru.yandex.practicum.filmorate.exceptions;

public class Error {
    private final String name;
    private final String description;

    public Error(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

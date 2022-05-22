package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private static int id;
    private static String name;
    private static String description;
    private static LocalDate releaseDate;
    private static Duration duration;
}

package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private static String login;
    private static String name;
    private static int id;
    private static String email;
    private static LocalDate birthday;

}

package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    String login;
    String name;
    int id;
    String email;
    LocalDate birthday;

}

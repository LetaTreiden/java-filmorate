package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String login;
    private String name;
    private int id;
    private String email;
    private LocalDate birthday;

}

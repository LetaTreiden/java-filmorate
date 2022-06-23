package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private String login;
    private String name;
    private int id;
    private String email;
    private LocalDate birthday;
    private Set<User> friends = new HashSet<>();

    public String toString() {
        return "Логин - " + getLogin() +
               "; ID - " + getId() +
               "; email - " + getEmail();
    }
}

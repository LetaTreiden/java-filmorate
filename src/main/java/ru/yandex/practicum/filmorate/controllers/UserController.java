package ru.yandex.practicum.filmorate.controllers;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<String, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(User.class);

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        String string;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            string = "Адрес электронной почты не может быть пустым.";
            log.info(string);
            throw new ValidationException(string);
        }
        if (users.containsKey(user.getEmail())) {
            string = ("Пользователь с такой электронной почтой уже зарегистрирован.");
            log.info(string);
            throw new ValidationException(string);
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            string = ("Логин не может быть пустым или содержать пробелы");
            log.info(string);
            throw new ValidationException(string);
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Имя равно логину");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            string = ("Дата рождения не может быть выбрана в будущем");
            log.info(string);
            throw new ValidationException(string);
        }
        users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        String string = null;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            string = "Адрес электронной почты не может быть пустым.";
            log.info(string);
            throw new ValidationException(string);
        }
        if (users.containsKey(user.getEmail())) {
            string = ("Пользователь с электронной почтой " + user.getEmail() + " уже зарегистрирован.");
            log.info(string);
            throw new ValidationException(string);
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            string = ("Логин не может быть пустым или содержать пробелы");
            log.info(string);
            throw new ValidationException(string);
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Имя равно логину");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            string = ("Дата рождения не может быть выбрана в будущем");
            log.info(string);
            throw new ValidationException(string);
        }
        users.put(user.getEmail(), user);
        return user;
    }
}


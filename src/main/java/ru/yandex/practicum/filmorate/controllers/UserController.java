package ru.yandex.practicum.filmorate.controllers;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private int count;

    @GetMapping
    public Map<Integer, User> findAll() {
        log.info("Список пользователей напечатан");
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        count++;
        user.setId(count);
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        if (users.containsKey(user.getId())) {
            validate(user);
            users.put(user.getId(), user);
        } else {
            log.info("Попытка обновить несуществующего пользователя");
            throw new ValidationException("Попытка обновить несуществующего пользователя");
        }

        return user;
    }

    private void validate(User user) throws ValidationException {
        String string;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            string = "Адрес электронной почты не может быть пустым.";
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
    }
}


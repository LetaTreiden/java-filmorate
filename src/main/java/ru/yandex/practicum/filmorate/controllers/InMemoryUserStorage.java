package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private int count;

    @Override
    public Collection<User> findAll() {
        log.info("Список пользователей напечатан");
        return users.values();
    }

    @Override
    public User create(@RequestBody User user) throws ValidationException {
        count++;
        user.setId(count);
        validate(user);
        users.put(user.getId(), user);
        log.info("Пользователь добавлен");
        return user;
    }

    @Override
    public User update(@RequestBody User user) throws ValidationException, NotFoundException {
        if (users.containsKey(user.getId())) {
            validate(user);
            users.put(user.getId(), user);
            log.info("Пользователь обновлен");
        } else {
            log.error("Попытка обновить несуществующего пользователя");
            throw new NotFoundException("Попытка обновить несуществующего пользователя");
        }

        return user;
    }

    @Override
    public User getById(int id)  {
        log.info("Пользователь напечатан");
        return users.get(id);
    }

    private void validate(User user) throws ValidationException {
        String string;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            string = "Адрес электронной почты не может быть пустым.";
            log.error(string);
            throw new ValidationException(string);
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            string = ("Логин не может быть пустым или содержать пробелы");
            log.error(string);
            throw new ValidationException(string);
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.error("Имя равно логину");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            string = ("Дата рождения не может быть выбрана в будущем");
            log.error(string);
            throw new ValidationException(string);
        }
    }
}

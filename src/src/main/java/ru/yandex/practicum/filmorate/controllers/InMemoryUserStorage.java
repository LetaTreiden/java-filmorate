package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.Validate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private int count;
    private final static Validate validator = new Validate();

    @Override
    public Collection<User> findAll() {
        log.info("Список пользователей напечатан");
        return users.values();
    }

    @Override
    public User create(@RequestBody User user) throws ValidationException {
        count++;
        user.setId(count);
        validator.validateUser(user);
        users.put(user.getId(), user);
        log.info("Пользователь добавлен");
        return user;
    }

    @Override
    public User update(@RequestBody User user) throws ValidationException, NotFoundException {
        if (users.containsKey(user.getId())) {
            validator.validateUser(user);
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
}
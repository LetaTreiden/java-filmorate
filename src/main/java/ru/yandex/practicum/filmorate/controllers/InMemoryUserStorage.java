package ru.yandex.practicum.filmorate.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int count;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(@RequestBody User user) throws ValidationException {
        count++;
        user.setId(count);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(@RequestBody User user) throws NotFoundException {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException("Попытка обновить несуществующего пользователя");
        }
        return user;
    }

    @Override
    public User getById(int id) throws NotFoundException {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundException("нет такого пользователя");
        }
    }
}

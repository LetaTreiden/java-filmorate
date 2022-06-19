package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    @GetMapping
    Collection<User> findAll();

    @PostMapping
    User create(@RequestBody User user) throws ValidationException;

    @PutMapping
    User update(@RequestBody User user) throws ValidationException;
}

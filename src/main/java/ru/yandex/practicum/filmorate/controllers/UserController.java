package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    UserService userService;

    //получить список всех пользователей
    @GetMapping
    public Collection<User> findAll() {
        return userService.getAll();
    }

    //список всех друзей пользователя
    @GetMapping("/{id}/friends")
    public Set<User> showFriends(@PathVariable int id) throws NotFoundException {
        return userService.getFriends(id);
    }

    //показать всех общих друзей
    @GetMapping("/{id}/friends/common/{otherId}")
    public List showMutual(@PathVariable int id, @PathVariable int otherId) throws NotFoundException {
        return userService.showMutualFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws NotFoundException {
        return userService.getUser(id);
    }

    //создать нового юзера
    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        return userService.create(user);
    }
    //обновить существующего юзера
    @PutMapping
    public User update(@RequestBody User user) throws ValidationException, NotFoundException {
        return userService.update(user);
    }
    //добавить нового друга
    @PutMapping("/{id}/friends/{friendId}")
    public void addNewFriend(@PathVariable int id, @PathVariable int friendId) throws NotFoundException {
        userService.addFriend(id, friendId);
    }

    //удалить друга
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) throws NotFoundException {
        userService.deleteFriend(id, friendId);
    }
}


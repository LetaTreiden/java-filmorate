package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping("/users")
public class UserController {
    InMemoryUserStorage userStorage;
    UserService userService;
    InMemoryFilmStorage filmStorage;

    @Autowired
    public UserController() {
        userStorage = new InMemoryUserStorage();
        filmStorage = new InMemoryFilmStorage();
        userService = new UserService(filmStorage, userStorage);
    }

    //получить список всех пользователей
    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    //список всех друзей пользователя
    @GetMapping("/{id}/friends")
    public Set<User> showFriends(@RequestBody User user) {
        return user.getFriends();
    }

    //показать всех общих друзей
    @GetMapping("/{id}/friends/common/{otherId}")
    public Set showMutual(@RequestBody User user1, @RequestBody User user2) {
        return userService.showMutualFriends(user1, user2);
    }

    //создать нового юзера
    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        return userStorage.create(user);
    }
    //обновить существующего юзера
    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        return userStorage.update(user);
    }
    //добавить нового друга
    @PutMapping("/{id}/friends/{friendId}")
    public void addNewFriend(@RequestBody User user1, @RequestBody User user2) throws ValidationException {
        userService.addFriend(user1, user2);
    }

    //удалить друга
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@RequestBody User user1, User user2) throws ValidationException {
        userService.deleteFriend(user1, user2);
    }
}


package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
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

    private final static Logger log = LoggerFactory.getLogger(User.class);

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
    public Set<Integer> showFriends(@PathVariable int id) {
        return userStorage.getById(id).getFriends();
    }

    //показать всех общих друзей
    @GetMapping("/{id}/friends/common/{otherId}")
    public Set showMutual(@PathVariable int id, @PathVariable int otherId) throws ValidationException, NotFoundException {
        return userService.showMutualFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        log.info("По идее процесс начался");
        return userStorage.getById(id);
    }

    //создать нового юзера
    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        return userStorage.create(user);
    }
    //обновить существующего юзера
    @PutMapping
    public User update(@RequestBody User user) throws ValidationException, NotFoundException {
        return userStorage.update(user);
    }
    //добавить нового друга
    @PutMapping("/{id}/friends/{friendId}")
    public void addNewFriend(@PathVariable int id, @PathVariable int friendId) throws ValidationException,
            NotFoundException {
        userService.addFriend(id, friendId);
    }

    //удалить друга
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) throws ValidationException,
            NotFoundException {
        userService.deleteFriend(id, friendId);
    }
}


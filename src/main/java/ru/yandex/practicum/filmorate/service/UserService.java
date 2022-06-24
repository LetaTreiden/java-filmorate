package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.Validator;

import java.util.*;

@Service
public class UserService {

    private final InMemoryUserStorage userStorage;
    private final static Validator validator = new Validator();
    private final static Logger log = LoggerFactory.getLogger(User.class);

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAll() {
        log.info("Список пользователей получен");
        return userStorage.findAll();
    }

    public Set<User> getFriends(int id) throws NotFoundException {
        Set<Integer> ids = userStorage.getById(id).getFriends();
        Set<User> friends = new HashSet<>();
        for (Integer id1 : ids) {
            friends.add(userStorage.getById(id1));
        }
        log.info("Список друзей получен");
        return friends;
    }

    public User getUser(int id) throws NotFoundException {
        validator.isUserExist(id, userStorage);
        log.info("Пользователь получен");
        return userStorage.getById(id);
    }

    public User create(User user) throws ValidationException {
        validator.validateUser(user);
        log.info("Пользователь создан");
        return userStorage.create(user);
    }

    public User update(User user) throws ValidationException, NotFoundException {
        validator.isUserExist(user.getId(), userStorage);
        validator.validateUser(user);
        log.info("Пользователь обновлен");
        return userStorage.update(user);
    }

    public User addFriend(Integer id1, Integer id2) throws NotFoundException {
        Set<Integer> friend1 = userStorage.getById(id2).getFriends();
        Set<Integer> friend2 = userStorage.getById(id1).getFriends();
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
        if (!userStorage.getById(id1).getFriends().contains(id2) && !userStorage.getById(id2).getFriends().contains(id2)) {
            friend1.add(id1);
            friend2.add(id2);

            userStorage.getById(id1).setFriends(friend2);
            userStorage.getById(id2).setFriends(friend1);
            log.info("Пользователи теперь друзья");
        } else {
            throw new NotFoundException("Пользователя/пользователей не существует");
        }
        return userStorage.getById(id1);
    }

    public User deleteFriend(int id1, int id2) throws NotFoundException {
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
        if (userStorage.getById(id1).getFriends().contains(id2)) {
            userStorage.getById(id1).getFriends().remove(id2);
        } else {
            throw new NotFoundException("Нет такого пользователя");
        }

        if (userStorage.getById(id2).getFriends().contains(id1)) {
            userStorage.getById(id2).getFriends().remove(id1);
        } else {
            throw new NotFoundException("Нет такого пользователя");
        }
        log.info("Пользователи больше не друзья");
        return userStorage.getById(id1);
    }

    public List showMutualFriends(int id1, int id2) throws NotFoundException {
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
        List<User> mutualFriends = new ArrayList<>();
        for (Integer id : userStorage.getById(id1).getFriends()) {
            if (userStorage.getById(id2).getFriends().contains(id)) {
                mutualFriends.add(userStorage.getById(id));
            }
        }
        log.info("Список общих друзей получен");
        return mutualFriends;
    }
}

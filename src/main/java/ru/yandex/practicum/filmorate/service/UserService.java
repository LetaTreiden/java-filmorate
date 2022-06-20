package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;
    private final static Logger log = LoggerFactory.getLogger(User.class);

    public UserService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    private void validate(User user) throws NotFoundException {
        String string;
        if (!userStorage.findAll().contains(user)) {
            string = "Пользователь " + user.getLogin() + " не существует";
            log.error(string);
            throw new NotFoundException(string);
        }
    }

    public void addFriend(Integer id1, Integer id2) throws ValidationException, NotFoundException {
        Set<Integer> friend1 = new HashSet<>();
        Set<Integer> friend2 = new HashSet<>();
        validate(userStorage.getById(id1));
        validate(userStorage.getById(id2));
            if (!userStorage.getById(id1).getFriends().contains(userStorage.getById(id2)) &&
                    !userStorage.getById(id2).getFriends().contains(userStorage.getById(id1))) {
                friend1.add(id1);
                friend2.add(id2);

                userStorage.getById(id1).setFriends(friend2);
                userStorage.getById(id2).setFriends(friend1);
            } else {
                throw new ValidationException("Пользователи " + userStorage.getById(id1).getLogin() + " и " +
                        userStorage.getById(id2).getLogin() + " уже друзья!");
            }
    }

    public void deleteFriend(int id1, int id2) throws ValidationException, NotFoundException {
        validate(userStorage.getById(id1));
        validate(userStorage.getById(id2));
            if (!userStorage.getById(id1).getFriends().contains(userStorage.getById(id2)) &&
                    userStorage.getById(id2).getFriends().contains(userStorage.getById(id1))) {
                userStorage.getById(id1).getFriends().remove(userStorage.getById(id2));
                userStorage.getById(id2).getFriends().remove(userStorage.getById(id2));
            } else {
                throw new ValidationException("Пользователи " + userStorage.getById(id1).getLogin() + " и " +
                        userStorage.getById(id2).getLogin() + " не друзья!");
            }
    }

    public Set showMutualFriends(int id1, int id2) throws NotFoundException {
        validate(userStorage.getById(id1));
        validate(userStorage.getById(id2));
        Set<Integer> mutualFriends = new HashSet<>();
        for (Integer id : userStorage.getById(id1).getFriends()) {
            if (userStorage.getById(id2).getFriends().contains(id)) {
                mutualFriends.add(id);
            }
        }
        return mutualFriends;
    }
}

package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    public UserService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addFriend(User user1, User user2) throws ValidationException {
        Set<User> friend1 = new HashSet<>();
        Set<User> friend2 = new HashSet<>();
        if (userStorage.findAll().contains(user1) && userStorage.findAll().contains(user2)) {
            if (!user1.getFriends().contains(user2) && !user2.getFriends().contains(user1)) {
                friend1.add(user1);
                friend2.add(user2);

                user1.setFriends(friend2);
                user2.setFriends(friend1);
            } else {
                throw new ValidationException("Пользователи " + user1.getLogin() + " и " + user2.getLogin() +
                        " уже друзья!");
            }
        } else {
            throw new ValidationException("Проверьте наличие пользователей. Аккаунт(ы) не зарегистрирован(ы)");
        }
    }

    public void deleteFriend(User user1, User user2) throws ValidationException {
        if (userStorage.findAll().contains(user1) && userStorage.findAll().contains(user2)) {
            if (user1.getFriends().contains(user2) && user2.getFriends().contains(user1)) {
                user1.getFriends().remove(user2);
                user2.getFriends().remove(user1);
            } else {
                throw new ValidationException("Пользователи " + user1.getLogin() + " и " + user2.getLogin() +
                        " не друзья!");
            }
        } else {
            throw new ValidationException("Проверьте наличие пользователей. Аккаунт(ы) не зарегистрирован(ы)");
        }
    }

    public Set showMutualFriends(User user1, User user2) {
        Set<User> mutualFriends = new HashSet<>();
        for (User user : user1.getFriends()) {
            if (user2.getFriends().contains(user)) {
                mutualFriends.add(user);
            }
        }
        return mutualFriends;
    }
}

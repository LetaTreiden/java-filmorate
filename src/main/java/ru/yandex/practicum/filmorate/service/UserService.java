package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.Validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final InMemoryUserStorage userStorage;
    private final static Validate validator = new Validate();

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAll() {
        return userStorage.findAll();
    }

    public Set<User> getFriends(int id) throws NotFoundException {
        Set<Integer> ids = userStorage.getById(id).getFriends();
        Set<User> friends = new HashSet<>();
        for(Integer id1 : ids){
            friends.add(userStorage.getById(id1));
        }
        return friends;
    }

    public User getUser(int id) throws NotFoundException {
        validator.isUserExist(id, userStorage);
        return userStorage.getById(id);
    }

    public User create(User user) throws ValidationException {
        validator.validateUser(user);
        return userStorage.create(user);
    }

    public User update(User user) throws ValidationException, NotFoundException {
        validator.validateUser(user);
        return userStorage.update(user);
    }

    public User addFriend(Integer id1, Integer id2) throws NotFoundException {
        Set<Integer> friend1 = userStorage.getById(id2).getFriends();
        Set<Integer> friend2 = userStorage.getById(id1).getFriends();
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
            if (!userStorage.getById(id1).getFriends().contains(id2) &&
                    !userStorage.getById(id2).getFriends().contains(id2)) {
                friend1.add(id1);
                friend2.add(id2);

                userStorage.getById(id1).setFriends(friend2);
                userStorage.getById(id2).setFriends(friend1);
            } else {
                throw new NotFoundException("Пользователя/пользователей не существует");
            }
        return userStorage.getById(id1);
    }

    public User deleteFriend(int id1, int id2) throws NotFoundException {
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
        Set<Integer> friend1 = userStorage.getById(id1).getFriends();
        Set<Integer> friend2 = userStorage.getById(id2).getFriends();
            if (userStorage.getById(id1).getFriends().contains(id2)) {
                friend1.remove(id2);
                friend2.remove(id1);

                userStorage.getById(id1).getFriends().clear();
                userStorage.getById(id1).setFriends(friend1);

                userStorage.getById(id2).getFriends().clear();
                userStorage.getById(id2).setFriends(friend2);
            } else {
                throw new NotFoundException("Пользователя/пользователей не существует");
            }
        return userStorage.getById(id1);
    }

    public Set showMutualFriends(int id1, int id2) throws NotFoundException {
        validator.isUserExist(id1, userStorage);
        validator.isUserExist(id2, userStorage);
        Set<User> user1 = getFriends(id1);
        Set<User> user2 = getFriends(id2);
        return user1.stream()
                .filter(user2::contains)
                .collect(Collectors.toSet());
    }
}

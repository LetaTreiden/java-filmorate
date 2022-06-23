package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.Validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final InMemoryUserStorage userStorage;
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private final static Validate validator = new Validate();

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAll() {
        return userStorage.findAll();
    }

    public Set<Integer> getFriends(int id) {
        return userStorage.getById(id).getFriends();
    }

    public User getUser(int id) throws NotFoundException {
        validator.isUserExist(id, userStorage);
        return userStorage.getById(id);
    }

    public User create(User user) throws ValidationException {
        return userStorage.create(user);
    }

    public User update(User user) throws ValidationException, NotFoundException {
        return userStorage.update(user);
    }

    public User addFriend(Integer id1, Integer id2) throws ValidationException, NotFoundException {

        log.info("Процесс добавления в друзья");
        Set<Integer> friend1 = new HashSet<>();
        Set<Integer> friend2 = new HashSet<>();
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
            if (!userStorage.getById(id1).getFriends().contains(id2) &&
                    !userStorage.getById(id2).getFriends().contains(id2)) {
                friend1.add(id1);
                friend2.add(id2);

                userStorage.getById(id1).setFriends(friend2);
                userStorage.getById(id2).setFriends(friend1);
            } else {
                throw new ValidationException("Пользователи " + userStorage.getById(id1).getLogin() + " и " +
                        userStorage.getById(id2).getLogin() + " уже друзья!");
            }
            return userStorage.getById(id1);
    }

    public void deleteFriend(int id1, int id2) throws ValidationException, NotFoundException {
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
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
        validator.isUserExist(userStorage.getById(id1).getId(), userStorage);
        validator.isUserExist(userStorage.getById(id2).getId(), userStorage);
        Set<Integer> mutualFriends = new HashSet<>();
        for (Integer id : userStorage.getById(id1).getFriends()) {
            if (userStorage.getById(id2).getFriends().contains(id)) {
                mutualFriends.add(id);
            }
        }
        return mutualFriends;
    }
}

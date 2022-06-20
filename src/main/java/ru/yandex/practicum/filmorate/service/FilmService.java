package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Service
public class FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;
    private Set<Film> rates = new TreeSet<>();

    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void like(Integer filmID, Integer userID) throws ValidationException, NotFoundException {
        isFilmsAndUsersExist(filmStorage.getById(filmID), userStorage.getById(userID));
        Set<User> newLike = new HashSet<>();
        newLike.add(userStorage.getById(userID));
        if (!filmStorage.getById(filmID).getLikes().contains(userStorage.getById(userID))) {
            filmStorage.getById(filmID).setLikes(newLike);
        } else {
            throw new ValidationException("Пользователь " + userStorage.getById(userID).getLogin() +
                    "  уже поставил лайк фильму " + filmStorage.getById(filmID).getName());
        }
    }

    public void dislike(Integer filmID, Integer userID) throws ValidationException, NotFoundException {
        isFilmsAndUsersExist(filmStorage.getById(filmID), userStorage.getById(userID));
        Set<User> newLike = filmStorage.getById(filmID).getLikes();
       if (newLike.contains(userStorage.getById(userID))) {
           newLike.remove(userStorage.getById(userID));
           filmStorage.getById(filmID).getLikes().clear();
           filmStorage.getById(filmID).setLikes(newLike);
       } else {
           throw new ValidationException("Пользователь " + userStorage.getById(userID).getLogin() +
                   " еще не ставил лайк фильму " + filmStorage.getById(filmID).getName());
       }
    }

    public Set getRate(Integer size) {
        Set<Film> rated = new HashSet<>();
        int count = 0;
        if (rates.size() > size) {
            for (Film film: rates) {
                while (count < size) {
                    rated.add(film);
                    count++;
                }
            }
        } else {
            rated.addAll(rates);
        }
        return rated;
    }

    private void isFilmsAndUsersExist(Film film, User user) throws NotFoundException {
        if (!filmStorage.getAll().contains(film)) {
            throw new NotFoundException("Нет такого фильма");
        }
        if (!userStorage.findAll().contains(user)) {
            throw new NotFoundException("Нет такого пользователя");
        }
    }
}

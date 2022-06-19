package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
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

    public void like(Film film, User user) throws ValidationException {
        isFilmsAndUsersExist(film, user);
        Set<User> newLike = new HashSet<>();
        newLike.add(user);
        if (!film.getLikes().contains(user)) {
            film.setLikes(newLike);
        } else {
            throw new ValidationException("Пользователь " + user.getLogin() + "  уже поставил лайк фильму "
                    + film.getName());
        }
    }

    public void dislike(Film film, User user) throws ValidationException {
        isFilmsAndUsersExist(film, user);
        Set<User> newLike = film.getLikes();
       if (newLike.contains(user)) {
           newLike.remove(user);
           film.getLikes().clear();
           film.setLikes(newLike);
       } else {
           throw new ValidationException("Пользователь " + user.getLogin() + " еще не ставил лайк фильму "
                   + film.getName());
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

    private void isFilmsAndUsersExist(Film film, User user) throws ValidationException {
        if (!filmStorage.getAll().contains(film)) {
            throw new ValidationException("Нет такого фильма");
        }
        if (!userStorage.findAll().contains(user)) {
            throw new ValidationException("Нет такого пользователя");
        }
    }
}

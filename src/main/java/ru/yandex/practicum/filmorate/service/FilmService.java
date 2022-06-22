package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.Validate;

import java.util.*;

@Service
public class FilmService {

    private final InMemoryFilmStorage filmStorage;
    private Set<Film> rates = new TreeSet<>();
    private final static Logger log = LoggerFactory.getLogger(User.class);
    private final static Validate validation = new Validate();

    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilm(int id) throws NotFoundException {
        validation.isFilmExist(id);
        return filmStorage.getById(id);
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film create(Film film) throws ValidationException {
        validation.validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException, NotFoundException {
        validation.validate(film);
        return filmStorage.update(film);
    }

    public void like(Integer filmID, Integer userID) throws ValidationException, NotFoundException {
        validation.isFilmExist(filmID);
        validation.isUserExist(userID);
        Set<Integer> newLike = new HashSet<>();
        newLike.add(userID);
        if (!filmStorage.getById(filmID).getLikes().contains(userID)) {
            filmStorage.getById(filmID).setLikes(newLike);
            log.info("Лайк поставлен");
        } else {
            throw new ValidationException("Пользователь уже поставил лайк фильму " +
                    filmStorage.getById(filmID).getName());
        }
    }

    public void dislike(Integer filmID, Integer userID) throws ValidationException, NotFoundException {
        validation.isFilmExist(filmID);
        validation.isUserExist(userID);
       if (filmStorage.getById(filmID).getLikes().contains(userID)) {
           filmStorage.getById(filmID).getLikes().clear();
       } else {
           throw new ValidationException("Пользователь еще не ставил лайк фильму " +
                   filmStorage.getById(filmID).getName());
       }
    }

    public Set getRate(Integer size) {
        Set<Film> rated = new HashSet<>();
        int count = 0;
        if (rates.size() < size) {
            for (Film film: rates) {
                while (count <= size) {
                    rated.add(film);
                    count++;
                }
            }
        } else {
            rated.addAll(rates);
        }
        return rated;
    }

}

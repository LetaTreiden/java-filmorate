package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.Validate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final static Logger log = LoggerFactory.getLogger(Film.class);

    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    private final Validate validation = new Validate();

    public Film getFilm(int id) throws NotFoundException {
        validation.isFilmExist(filmStorage.getById(id), filmStorage);
        log.info("Фильм получен");
        return filmStorage.getById(id);
    }

    public Collection<Film> getAll() {
        log.info("Список фильмов получен");
        return filmStorage.getAll();
    }

    public Film create(Film film) throws ValidationException {
        validation.validateFilm(film);
        log.info("Фильм создан");
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException, NotFoundException {
        validation.isFilmExist(film, filmStorage);
        validation.validateFilm(film);
        log.info("Фильм обновлен");
        return filmStorage.update(film);
    }

    public void like(Integer filmID, Integer userID) throws NotFoundException {
        Film film = filmStorage.getById(filmID);
        validation.isFilmExist(film, filmStorage);
        Set<Integer> newLike = new HashSet<>();
        newLike.add(userID);
        if (!filmStorage.getById(filmID).getLikes().contains(userID)) {
            filmStorage.getById(filmID).setLikes(newLike);
            log.info("Лайк поставлен");
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public void dislike(Integer filmID, Integer userID) throws NotFoundException {
        Film film = filmStorage.getById(filmID);
        validation.isFilmExist(film, filmStorage);
        if (filmStorage.getById(filmID).getLikes().contains(userID)) {
            filmStorage.getById(filmID).getLikes().clear();
            log.info("Лайк удален");
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public List<Film> getRate(Integer count) {
        log.info("Список по рейтингу фильмов получен");
        return filmStorage.getFilms()
                .values()
                .stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(Objects.requireNonNullElse(count, 10))
                .collect(Collectors.toList());
    }
}

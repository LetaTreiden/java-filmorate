package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    private int count = 0;

    public Film getById(int id) throws NotFoundException {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException("Нет такого фильма");
        }
    }

    @Override
    public Map<Integer, Film> getAll() {
        logger.info("Выведен список всех фильмов");
        return films;
    }

    @Override
    public Film create(@RequestBody Film film) throws ValidationException {
        count++;
        film.setId(count);
        films.put(film.getId(), film);
        logger.info("Фильм добавлен");
        return film;
    }

    @Override
    public Film update(@RequestBody Film film) {
        films.put(film.getId(), film);
        return film;
    }
}

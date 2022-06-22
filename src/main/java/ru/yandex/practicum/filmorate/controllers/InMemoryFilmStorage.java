package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.Validate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    private int count = 0;
    private static final Validate validator = new Validate();

    public Film getById(int id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getAll() {
        logger.info("Выведен список всех фильмов");
        return films.values();
    }

    @Override
    public Film create(@RequestBody Film film) throws ValidationException {
        count++;
        film.setId(count);
        validator.validate(film);
        films.put(film.getId(), film);
        logger.info("Фильм добавлен");
        return film;
    }

    @Override
    public Film update(@RequestBody Film film) throws ValidationException, NotFoundException {
        if (films.containsKey(film.getId())) {
            validator.validate(film);
            films.put(film.getId(), film);
            logger.info("Фильм обновлен");
        } else {
            logger.error("Попытка обновить несуществующий фильм");
            throw new NotFoundException("Попытка обновить несуществующий фильм");
        }
        return film;
    }


}

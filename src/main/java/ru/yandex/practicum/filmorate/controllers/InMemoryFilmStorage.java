package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    private static final LocalDate FIST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private int count = 0;

    public Film getById(int id) {
        return films.get(id);
    }

    @GetMapping
    public Collection<Film> getAll() {
        logger.info("Выведен список всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        count++;
        film.setId(count);
        validate(film);
        films.put(film.getId(), film);
        logger.info("Фильм добавлен");
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            validate(film);
            films.put(film.getId(), film);
            logger.info("Фильм обновлен");
        } else {
            logger.error("Попытка обновить несуществующий фильм");
            throw new ValidationException("Попытка обновить несуществующий фильм");
        }
        return film;
    }

    private void validate(Film film) throws ValidationException {
        String string;
        if (film.getName().isEmpty() || film.getName() == null) {
            string = "Название не может быть пустым";
            logger.error(string);
            throw new ValidationException(string);
        }
        if (film.getDescription().length() > 200) {
            string = "Слишком длинное описание. Введите меньше 200 символов";
            logger.error(string);
            throw new ValidationException(string);
        }
        if (film.getReleaseDate().isBefore(FIST_RELEASE_DATE)) {
            string = "Дата релиза не может быть раньше 28 декабря 1895";
            logger.error(string);
            throw new ValidationException(string);
        }
        if (film.getDuration() < 0) {
            string = "Длительность фильма не может быть отрицательной";
            logger.error(string);
            throw new ValidationException(string);
        }
    }
}

package ru.yandex.practicum.filmorate.controllers;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Film.class);

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping
    public void create(@RequestBody Film film) throws ValidationException {
        String string;
        if (film.getName().isEmpty() || film.getName() == null) {
            string = "Название не может быть пустым";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getDescription().length() > 200) {
            string = "Слишком длинное описание. Введите меньше 200 символов";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            string = "Дата релиза не может быть раньше 28 декабря 1895";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getDuration().isNegative()) {
            string = "Длительность фильма не может быть отрицательной";
            logger.info(string);
            throw new ValidationException(string);
        }
        films.put(film.getId(), film);
    }

    @PutMapping
    public void update(@RequestBody Film film) throws ValidationException {
        String string;
        if (film.getName().isEmpty() || film.getName() == null) {
            string = "Название не может быть пустым";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getDescription().length() > 200) {
            string = "Слишком длинное описание. Введите меньше 200 символов";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            string = "Дата релиза не может быть раньше 28 декабря 1895";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getDuration().isNegative()) {
            string = "Длительность фильма не может быть отрицательной";
            logger.info(string);
            throw new ValidationException(string);
        }
        films.put(film.getId(), film);
    }
}

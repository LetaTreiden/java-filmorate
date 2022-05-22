package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    private static final LocalDate firstReleaseDate = LocalDate.of(1895, 12, 28);
    private int count = 0;

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        count++;
        film.setId(count);
        validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
         if (films.containsKey(film.getId())) {
            validate(film);
            films.put(film.getId(), film);
        } else {
            logger.info("Попытка обновить несуществующий фильм");
            throw new ValidationException("Попытка обновить несуществующий фильм");
        }
        return film;
    }

    private void validate(Film film) throws ValidationException {
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
        if (film.getReleaseDate().isBefore(firstReleaseDate)) {
            string = "Дата релиза не может быть раньше 28 декабря 1895";
            logger.info(string);
            throw new ValidationException(string);
        }
        if (film.getDuration() < 0) {
            string = "Длительность фильма не может быть отрицательной";
            logger.info(string);
            throw new ValidationException(string);
        }
    }
}

package ru.yandex.practicum.filmorate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class Validate {
    InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    private static final LocalDate FIST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    String string;
    public void validate(Film film) throws ValidationException {
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
    public void isFilmExist(int id) throws NotFoundException {
        if (!filmStorage.getAll().contains(filmStorage.getById(id))) {
            string = "Такого фильма не существует";
            logger.error(string);
            throw new NotFoundException(string);
        }
    }

    public void isUserExist(int id) throws NotFoundException{
        if (!userStorage.findAll().contains(userStorage.getById(id))) {
            string = "Такого пользователя не существует";
            logger.error(string);
            throw new NotFoundException(string);
        }
    }
}

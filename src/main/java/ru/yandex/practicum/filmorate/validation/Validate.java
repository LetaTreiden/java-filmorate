package ru.yandex.practicum.filmorate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controllers.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
public class Validate {
    private static final Logger logger = LoggerFactory.getLogger(Film.class);
    private static final LocalDate FIST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    String string;
    public void validateFilm(Film film) throws ValidationException {
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

    public void validateUser(User user) throws ValidationException {
        String string;
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            string = "Адрес электронной почты не может быть пустым.";
            logger.error(string);
            throw new ValidationException(string);
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            string = ("Логин не может быть пустым или содержать пробелы");
            logger.error(string);
            throw new ValidationException(string);
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            logger.error("Имя равно логину");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            string = ("Дата рождения не может быть выбрана в будущем");
            logger.error(string);
            throw new ValidationException(string);
        }
    }
    public void isFilmExist(Film film, InMemoryFilmStorage filmStorage) throws NotFoundException {
        logger.info("start2");
        System.out.println(film);
        System.out.println(filmStorage.getAll());
        if (film.getId() <= 0 || !filmStorage.getAll().containsKey(film.getId())) {
            logger.info("ghjk");
            throw new NotFoundException("Нет фильма с таким ID.");
        }
        logger.info("fine");
    }

    public void isUserExist(int id, InMemoryUserStorage userStorage) throws NotFoundException{
        if (!userStorage.findAll().contains(userStorage.getById(id))) {
            string = "Такого пользователя не существует";
            logger.error(string);
            throw new NotFoundException(string);
        }
    }
}

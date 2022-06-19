package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController implements FilmStorage {
    InMemoryFilmStorage filmStorage;
    FilmService filmService;
    InMemoryUserStorage userStorage;
    @Autowired
    public FilmController() {
        filmStorage = new InMemoryFilmStorage();
        userStorage = new InMemoryUserStorage();
        filmService = new FilmService(filmStorage,userStorage);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

   @GetMapping("/popular?count={count}")
   public Collection<Film> printBestCount(@RequestBody Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }
        return filmService.getRate(count);
   }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        return filmStorage.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void newLike(@RequestBody Film film, @RequestBody User user) throws ValidationException {
        filmService.like(film, user);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@RequestBody Film film, @RequestBody User user) throws ValidationException {
        filmService.dislike(film, user);
    }
}

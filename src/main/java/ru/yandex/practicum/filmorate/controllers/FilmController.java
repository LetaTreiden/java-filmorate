package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController implements FilmStorage {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) throws NotFoundException {
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> printBestCount(@RequestParam(required = false) Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }
        return filmService.getRate(count);
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException, NotFoundException {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void newLike(@PathVariable int id, @PathVariable int userId) throws ValidationException, NotFoundException {
        filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) throws ValidationException, NotFoundException {
        filmService.dislike(id, userId);
    }

}

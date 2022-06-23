package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {
    @GetMapping
    Map<Integer, Film> getAll();

    @PostMapping
    Film create(@RequestBody Film film) throws ValidationException;

    @PutMapping
    Film update(@RequestBody Film film) throws ValidationException, NotFoundException;
}

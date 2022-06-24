package ru.yandex.practicum.filmorate.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int count = 0;

    public Film getById(int id) throws NotFoundException {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException("Нет такого фильма");
        }
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Film create(@RequestBody Film film) throws ValidationException {
        count++;
        film.setId(count);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(@RequestBody Film film) {
        films.put(film.getId(), film);
        return film;
    }
}

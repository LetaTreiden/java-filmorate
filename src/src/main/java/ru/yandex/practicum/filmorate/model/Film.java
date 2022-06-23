package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private String name;
    private int id;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes = new HashSet<>();

    public int compareTo(Film o) {
        int thisSize = this.getLikes().size();
        int otherSize = o.getLikes().size();
        return thisSize - otherSize;
    }
}

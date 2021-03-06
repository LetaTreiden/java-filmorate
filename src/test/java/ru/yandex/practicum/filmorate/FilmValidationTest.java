package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class FilmValidationTest {

    private final FilmController filmController = new FilmController();

    @Test
    public void createIfEverythingIsOk() throws ValidationException {
        Film film1 = new Film();
        film1.setName("Прошлой ночью в Сохо");
        film1.setDescription("Грезы о Лондоне 1960-х оборачиваются для Элоиз кошмарной реальностью.");
        film1.setReleaseDate(LocalDate.of(2021, 9, 4));
        film1.setDuration(116);

        filmController.create(film1);
        Assertions.assertEquals(1, filmController.getAll().size());
    }

    @Test
    public void createIfNameIsNull() {
        Film film = new Film();
        film.setDescription("Писателя в депрессии преследует таинственный подражатель");
        film.setReleaseDate(LocalDate.of(2004, 3, 7));
        film.setDuration(96);

        try {
            filmController.create(film);
            Assertions.fail("Название не может быть пустым");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void createIfDescriptionIsTooLong() {
        Film film = new Film();
        film.setName("Взрывная блондинка");
        film.setDescription("Агент Лоррейн Бротон, бриллиант в короне Секретной разведывательной службы Ее " +
                "Величества, не просто мастер шпионажа: она бомбически сексуальна, взрывоопасна и использует весь " +
                "арсенал своих уникальных умений во время невыполнимых миссий. В неспокойном Берлине, куда ее " +
                "направляют с заданием вернуть секретное досье, она вынуждена объединиться с агентом под прикрытием " +
                "Дэвидом Персивалем. Вместе им предстоит проложить путь через тернии смертельных шпионских игр.");
        film.setReleaseDate(LocalDate.of(2017, 3, 12));
        film.setDuration(115);

        try {
            filmController.create(film);
            Assertions.fail("Слишком длинное описание. Введите меньше 200 символов");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void createIfReleaseDateIsImpossible() {
        Film film = new Film();
        film.setName("Триггер");
        film.setDescription("Страшная трагедия вынуждает психолога-провокатора вернуться к практике.");
        film.setReleaseDate(LocalDate.of(1894, 3, 12));
        film.setDuration(52);

        try {
            filmController.create(film);
            Assertions.fail("Дата релиза не может быть раньше 28 декабря 1895");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void createIfDurationIsNegative() {
        Film film = new Film();
        film.setName("Мистер Робот");
        film.setDescription("Угрюмый хакер борется с капитализмом и самим собой.");
        film.setReleaseDate(LocalDate.of(2015, 3, 17));
        film.setDuration(-1);

        try {
            filmController.create(film);
            Assertions.fail("Длительность фильма не может быть отрицательной");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }
}

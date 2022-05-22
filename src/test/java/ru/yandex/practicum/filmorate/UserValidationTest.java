package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserValidationTest {
    private static final UserController controller = new UserController();

    public void createIfEverythingIsOk() throws ValidationException {
        User user = new User();
        user.setName("Лина");
        user.setEmail("treidenp@yandex.ru");
        user.setLogin("LetaT");
        user.setBirthday(LocalDate.of(2002, 10, 25));

        Assertions.assertEquals(user, controller.create(user));
        Assertions.assertEquals(1, controller.findAll().size());
    }

    @Test
    public void createIfEmailIsEmpty() {
        User user = new User();
        user.setName("Надя");
        user.setLogin("NadyaTreiden");
        user.setBirthday(LocalDate.of(1977, 8, 7));

        try {
            controller.create(user);
            Assertions.fail("Адрес электронной почты не может быть пустым.");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void createIfEmailIsAlreadyExist() throws ValidationException {
        createIfEverythingIsOk();
        User user = new User();
        user.setName("Ева");
        user.setEmail("treidenp@yandex.ru");
        user.setLogin("evanepran");
        user.setBirthday(LocalDate.of(2002, 9, 3));

        try {
            controller.create(user);
            Assertions.fail("Пользователь с такой электронной почтой уже зарегистрирован.");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void createIfLoginIsEmpty() {
        User user = new User();
        user.setName("Ира");
        user.setEmail("i.odnoshivkina@yandex.ru");
        user.setBirthday(LocalDate.of(2002, 10, 25));

        try {
            controller.create(user);
            Assertions.fail("Логин не может быть пустым или содержать пробелы");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void createIfBirthdayInFuture() {
        User user = new User();
        user.setName("Соня");
        user.setLogin("SEremina");
        user.setEmail("seremina@yandex.ru");
        user.setBirthday(LocalDate.of(2023, 2, 4));

        try {
            controller.create(user);
            Assertions.fail("Дата рождения не может быть выбрана в будущем");
        } catch (Exception e) {
            Assertions.assertNotEquals("", e.getMessage());
        }
    }
}

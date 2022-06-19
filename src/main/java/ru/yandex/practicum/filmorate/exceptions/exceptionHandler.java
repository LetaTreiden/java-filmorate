package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice("ru.yandex.practicum.filmorate")
public class exceptionHandler {

    //404, ошибка валидации
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleValidationError(final ValidationException e) {
        return new Error("ошибка валидации", e.getMessage());
    }

    //404, искомый объект не найден
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleSearchError(final ValidationException e) {
        return new Error("объект не найден", e.getMessage());
    }

    //500, выброшено исключение
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(final Throwable e) {
        return new Error("исключение", e.getMessage());
    }

}
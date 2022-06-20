package ru.yandex.practicum.filmorate.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice("ru.yandex.practicum.filmorate")
public class ErrorHandler {

    //400, Ошибка валидации
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidate(final ValidationException e) {
        return new ErrorResponse("Ошибка валидации", e.getMessage());
    }

    //404, Ошибка поиска
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        return new ErrorResponse("Ошибка поиска", e.getMessage());
    }

    //500, Исключение
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Throwable e) {
        return new ErrorResponse("Исключение", e.getMessage());
    }
}
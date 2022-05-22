package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.controllers.FilmController;

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		System.out.println(FilmController.class);
			SpringApplication.run(FilmorateApplication.class, args);
	}

}

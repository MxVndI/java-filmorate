package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.model.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

public class FilmControllerTest {
    FilmStorage filmStorage = new InMemoryFilmStorage();
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage);
    UserController userController = new UserController(userService);
    FilmService filmService = new FilmService(filmStorage, userStorage);
    FilmController filmController = new FilmController(filmService);

    @Test
    void addFilmTest() {
        Film film = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999, 2, 5), 87);
        filmController.addFilm(film);
        Assertions.assertNotNull(filmController.getFilms());
    }

    @Test
    void updateFilmTest() {
        Film film = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999, 2, 5), 87);
        filmController.addFilm(film);
        int id = filmStorage.getFilmById(film.getId()).getId();
        film = new Film("sdfds", "fdsfsdfdsfds", LocalDate.of(1999, 2, 5), 87);
        film.setId(id);
        filmController.updateFilm(film);
        Assertions.assertEquals(filmStorage.getFilmById(film.getId()), film);
    }

    @Test
    void likeFilmTest() {
        Film film = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999, 2, 5), 87);
        filmController.addFilm(film);
        User user1 = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user1);
        filmController.likeFilm(film.getId(), user1.getId());
        Assertions.assertEquals(filmStorage.getFilmById(film.getId()).getLikes().size(), 1);
    }

    @Test
    void deleteLikeFilmTest() {
        Film film = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999, 2, 5), 87);
        filmController.addFilm(film);
        User user1 = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user1);
        filmController.likeFilm(film.getId(), user1.getId());
        filmController.removeLike(film.getId(), user1.getId());
        Assertions.assertNotEquals(filmStorage.getFilmById(film.getId()).getLikes().size(), 1);
    }

    @Test
    void getPopularFilmTest() {
        Film film = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999, 2, 5), 87);
        filmController.addFilm(film);
        User user1 = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user1);
        filmController.likeFilm(film.getId(), user1.getId());
        Film film2 = new Film("fdgd", "fdsfsdfdsfds", LocalDate.of(2009, 3, 5), 87);
        filmController.addFilm(film2);
        Assertions.assertNotEquals(filmController.getPopularFilms(2), null);
    }
}

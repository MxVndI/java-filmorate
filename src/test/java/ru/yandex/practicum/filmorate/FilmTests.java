package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmTests {

    @Test
    void createFilmTest() {
        Film film = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999,2,5), 87);
        Assertions.assertNotNull(film);
    }

    @Test
    void equalsUsersTest() {
        Film film1 = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999,2,5), 87);
        Film film2 = new Film("utyuyt", "fdsfsdfdsfds", LocalDate.of(1999,2,5), 87);
        Assertions.assertEquals(film1, film2);
    }
}

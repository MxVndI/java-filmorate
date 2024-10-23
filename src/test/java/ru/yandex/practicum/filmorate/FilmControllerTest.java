package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@SpringBootTest
public class FilmControllerTest {
    @Autowired
    private FilmController filmController;

    @Test
    public void ifFilmNameIsBlankThenThrowException() {
        Film film = Film.builder().name("").duration(1)
                .releaseDate(LocalDate.of(1994, 1, 1)).build();
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        Assertions.assertEquals("Имя не может быть пустым", exception.getMessage());
    }

    @Test
    public void ifFilmDescriptionLengthIsMoreThen200ThenThrowException() {
        Film film = Film.builder().name("Побег из Шоушенка").duration(1)
                .releaseDate(LocalDate.of(1994, 1, 1)).build();
        film.setDescription("Фильм «Побег из Шоушенка» (1994) — это культовая драма, снятая по мотивам повести Стивена Кинга. " +
                "В центре сюжета — история Энди Дюфрейна, банкира, несправедливо обвинённого в убийстве жены и приговорённого к пожизненному заключению в тюрьме Шоушенк. Несмотря на жестокость и безнадёжность тюремной жизни, " +
                "Энди не теряет надежды и находит способы выжить и даже обрести свободу. Фильм получил множество наград и считается одним из лучших в истории кинематографа.");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void ifFilmReleaseDateIsBefore1895_12_28ThenThrowException() {
        Film film = Film.builder().name("Побег из Шоушенка").duration(1)
                .releaseDate(LocalDate.of(1794, 1, 1)).build();
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void ifFilmDurationIsNegativeThenThrowException() {
        Film film = Film.builder().name("Побег из Шоушенка").duration(-1)
                .releaseDate(LocalDate.of(1994, 1, 1)).build();
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> filmController.addFilm(film));
    }

}
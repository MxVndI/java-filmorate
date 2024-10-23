package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(of = "name")
@Builder(toBuilder = true)
public class Film implements Comparable<Film> {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private List<Like> likes;
    private List<Genre> genres;
    private Mpa mpa;
    private Integer rate;

    @Override
    public int compareTo(Film other) {
        return Integer.compare(other.getLikes().size(), this.getLikes().size());
    }
}

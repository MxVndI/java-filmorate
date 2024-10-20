package ru.yandex.practicum.filmorate.storage.rating;

import ru.yandex.practicum.filmorate.model.Rating;

public interface RatingStorage {

    void addRating(String name);

    Rating getRatingById(Integer filmId);

    void deleteRatingById(Integer filmId);
}

package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.Collection;

public interface LikeStorage {
    void addLike(Integer filmId, Integer userId);

    boolean removeLike(Integer filmId, Integer userId);

    Collection<Like> getLikesFilmId(Integer filmId);
}

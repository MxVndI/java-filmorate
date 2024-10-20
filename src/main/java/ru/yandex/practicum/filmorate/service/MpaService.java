package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor

public class MpaService {

    private static final String NOT_FOUND_MESSAGE = "Rating рейтинга с id %s нет";
    private final MpaStorage mpaStorage;

    public Mpa getRatingById(Integer id) {
        return mpaStorage.getRatingById(id);
    }

    public Collection<Mpa> getAllRating() {
        return mpaStorage.getRatings();
    }

}

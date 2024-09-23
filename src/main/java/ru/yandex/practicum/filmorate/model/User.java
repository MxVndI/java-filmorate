package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    private String name;
    @NotBlank
    private String login;
    @Email
    private String email;
    private LocalDate birthday;

    public User(String login, String email, LocalDate birthday) {
        this.name = login;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }

    public User(String name, String login, String email, LocalDate birthday) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }
}
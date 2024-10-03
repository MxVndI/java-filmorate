package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
public class User {
    private int id;
    private String name;
    @NotBlank
    private String login;
    @Email
    private String email;
    private LocalDate birthday;
    @JsonIgnore
    public Set<User> friends = new HashSet<>();

    public User(String login, String email, LocalDate birthday) {
        this.name = login;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }

}
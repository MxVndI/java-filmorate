package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, birthday);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User guest = (User) obj;
        return id == (guest.getId()) && name.equals(guest.getName()) && login.equals(guest.getLogin()) &&
                email.equals(guest.getEmail()) && birthday.equals(guest.birthday);
    }
}
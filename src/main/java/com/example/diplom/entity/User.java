package com.example.diplom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;
    @Column(name = "PasswordHash")
    private String password;

    private String phone;

    private String role = "ROLE_USER";

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = getPhone();
        this.password = password;
    }
}
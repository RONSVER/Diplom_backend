package com.superstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Cart> carts;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Favorite> favorites;

    public enum Role {
        Client, Administrator
    }
}
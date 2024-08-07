package com.superstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;
    private String category_name;

    public Category(Long category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }
}


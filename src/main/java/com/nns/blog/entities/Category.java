package com.nns.blog.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(name = "category_title", length = 100, nullable = false)
    private String categoryTitle;
    @Column(name = "description")
    private String categoryDesc;

}
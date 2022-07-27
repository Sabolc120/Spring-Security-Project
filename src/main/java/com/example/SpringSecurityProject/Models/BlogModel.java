package com.example.SpringSecurityProject.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BlogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Lob
    private String blogPost;

    private String postDate;

    @Override
    public String toString() {
        return "BlogModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", blogPost='" + blogPost + '\'' +
                ", postDate=" + postDate +
                '}';
    }
}

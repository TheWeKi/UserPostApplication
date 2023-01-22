package com.weki.socialapplication.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weki.socialapplication.user.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "social_user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Past(message = "Birth Date Must be in Past")
    private LocalDate birthDate;

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> post;

    public User() {
    }

    public User(Integer id, String name, LocalDate birthDate, List<Post> post) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.post = post;
    }

    public User(List<Post> post) {
        this.post = post;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

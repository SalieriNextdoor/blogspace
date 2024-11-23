package com.blogspace.MVC.User;

import com.blogspace.MVC.Project.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An entity class represents a table in a relational database
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users", schema="userschema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String password;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Project> projects;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public boolean hasNull() {
        return email == null || username == null || password == null;
    }
}

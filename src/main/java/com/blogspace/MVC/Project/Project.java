package com.blogspace.MVC.Project;

import com.blogspace.MVC.Post.Post;
import com.blogspace.MVC.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "projects", schema="projectschema")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false, unique = true)
    private String description;
    @Column(nullable = false, unique = true)
    private String image;
    private LocalDateTime createdAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public boolean hasNull() { return title == null || description == null || image == null; }
}

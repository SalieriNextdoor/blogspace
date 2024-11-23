package com.blogspace.MVC.Post;

import com.blogspace.MVC.Project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "posts", schema="projectschema")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false, unique = true)
    private String image;
    @Column(nullable = false, unique = true)
    private String text;
    private LocalDateTime createdAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;

    public boolean hasNull() {
        return (title == null || image == null || text == null);
    }
}

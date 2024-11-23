package com.blogspace.MVC.Post;

import com.blogspace.MVC.Project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String image;
    private String text;
    private LocalDateTime createdAt;
    private ProjectDTO project;
}

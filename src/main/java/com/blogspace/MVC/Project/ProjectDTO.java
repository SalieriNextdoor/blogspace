package com.blogspace.MVC.Project;

import com.blogspace.MVC.Post.PostDTO;
import com.blogspace.MVC.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private LocalDateTime createdAt;
    private UserDTO user;
    private List<PostDTO> posts;
}

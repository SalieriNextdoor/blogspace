package com.blogspace.MVC.User;

import com.blogspace.MVC.Project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private List<ProjectDTO> projects;
}

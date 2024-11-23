package com.blogspace.MVC.Project;

import com.blogspace.MVC.Post.Post;
import com.blogspace.MVC.Post.PostDTO;
import com.blogspace.MVC.User.User;
import com.blogspace.MVC.User.UserDTO;
import com.blogspace.MVC.User.UserService;
import com.blogspace.util.exception.BadArgumentException;
import com.blogspace.util.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Validated
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;


    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/project
     * Purpose: Fetches all projects
     * @return List of all projects
     */
    @GetMapping("/")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();

            List<ProjectDTO> projectDTOs = projects.stream()
                    .map(this::convertToDTO)
                    .toList();
            return ResponseEntity.ok(projectDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/project/:id/posts
     * Purpose: Fetches all posts under a project
     * @param id - Project id
     * @return List of all posts under given project
     */
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable Long id) {
        try {
            List<PostDTO> postDTOs = projectService.getAllPostsFromProjectById(id).stream()
                    .map(this::convertToDTO)
                    .toList();

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/project/:id
     * Purpose: Fetches a specific project
     * @param id - Project id
     * @return Project entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        try {
            Project project = projectService.getProjectById(id);

            return ResponseEntity.ok(convertToDTO(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a POST request is made
     * URL: localhost:8080/api/project
     * Purpose: Creates a new project
     * @param project - Request body project entity
     * @return Project entity
     */
    @PostMapping("/")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) {
        if (project.hasNull())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            User user = userService.getUserByEmail(userEmail);

            Project createdProject = projectService.createProject(project, user);

            return ResponseEntity.ok(convertToDTO(createdProject));
        } catch (BadArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a PUT request is made
     * URL: localhost:8080/api/project/:id
     * Purpose: Updates an existing project
     * @param project - Request body project entity
     * @return Project entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> modifyProject(@PathVariable Long id, @RequestBody Project project) {
        if (project.hasNull())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userService.getUserByEmail(userEmail);

            Project updatedProject = projectService.updateProject(id, user, project);

            return ResponseEntity.ok(convertToDTO(updatedProject));
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (BadArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a DELETE request is made
     * URL: localhost:8080/api/project/:id
     * Purpose: Deletes a project
     * @return a String message indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userService.getUserByEmail(userEmail);

            projectService.deleteProject(id, user);

            return ResponseEntity.ok().body("Project deleted successfully.");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (BadArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private ProjectDTO convertToDTO(Project project) {
        User user = project.getUser();
        List<PostDTO> postDTOs = project.getPosts().stream()
                .map(this::convertToDTO)
                .toList();

        return new ProjectDTO(
                project.getId(), project.getTitle(), project.getDescription(), project.getImage(), project.getCreatedAt(),
                new UserDTO(
                        user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), null)
                , postDTOs);
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(), post.getTitle(), post.getImage(), post.getText(), post.getCreatedAt(), null);
    }
}

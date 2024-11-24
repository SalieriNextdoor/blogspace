package com.blogspace.MVC.Post;

import com.blogspace.MVC.Project.Project;
import com.blogspace.MVC.Project.ProjectDTO;
import com.blogspace.MVC.Project.ProjectService;
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

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;
    private final ProjectService projectService;
    private final UserService userService;

    /**
     * This method is called when a GET request is made
     * URL: localhost:8080/api/project/:project_id/posts/:id
     * Purpose: Fetches a specific post
     * @param id - Post id
     * @param projectId - Project under which the post is located
     * @return Post entity
     */
    @GetMapping("/{projectId}/posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id, @SuppressWarnings("unused") @PathVariable String projectId) {
        try {
            Post post = postService.getPostById(id);

            return ResponseEntity.ok(convertToDTO(post));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a POST request is made
     * URL: localhost:8080/api/project/:project_id/posts
     * Purpose: Creates a new post
     * @param post - Request body post entity
     * @param projectId - Project under which the post will be published
     * @return Post entity
     */
    @PostMapping("/{projectId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody Post post, @PathVariable Long projectId) {
        if (post.hasNull())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            User user = userService.getUserByEmail(userEmail);
            Project project = projectService.getProjectById(projectId);

            Post createdPost = postService.createPost(post, project, user);

            return ResponseEntity.ok(convertToDTO(createdPost));
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (BadArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is called when a PUT request is made
     * URL: localhost:8080/api/project/:project_id/posts/:id
     * Purpose: Updates an existing post
     * @param post - Request body post entity
     * @param projectId - Project under which the post is located
     * @return Project entity
     */
    @PutMapping("/{projectId}/posts/{id}")
    public ResponseEntity<PostDTO> modifyPost(@PathVariable Long id, @RequestBody Post post, @SuppressWarnings("unused") @PathVariable String projectId) {
        if (post.hasNull())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userService.getUserByEmail(userEmail);

            Post updatedPost = postService.updatePost(id, post, user);

            return ResponseEntity.ok(convertToDTO(updatedPost));
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
     * URL: localhost:8080/api/project/:project_id/posts/:id
     * @param projectId - Project under which the post is located
     * Purpose: Deletes a post
     * @return Project entity
     */
    @DeleteMapping("/{projectId}/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @SuppressWarnings("unused") @PathVariable String projectId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userService.getUserByEmail(userEmail);

            postService.deletePost(id, user);

            return ResponseEntity.ok().body("Post deleted successfully.");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (BadArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private PostDTO convertToDTO(Post post) {
        Project project = post.getProject();
        User user = project.getUser();
        return new PostDTO(
                post.getId(), post.getTitle(), post.getImage(), post.getText(), post.getCreatedAt(),
                new ProjectDTO(project.getId(), project.getTitle(), project.getDescription(), project.getImage(), project.getCreatedAt(),
                        new UserDTO(
                                user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), null), null));
    }
}

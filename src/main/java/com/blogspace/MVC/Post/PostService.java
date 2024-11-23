package com.blogspace.MVC.Post;

import com.blogspace.MVC.Project.Project;
import com.blogspace.MVC.User.User;
import com.blogspace.util.exception.BadArgumentException;
import com.blogspace.util.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepo postRepo;

    public Post getPostById(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new BadArgumentException("Post with id " + id + " does not exist."));
    }

    public Post createPost(Post post, Project project, User user) {
        post.setCreatedAt(LocalDateTime.now());
        post.setProject(project);

        if (!user.getId().equals(project.getUser().getId())) {
            log.error("User with id {} does not have permission to create a post under project with id {}.", user.getId(), project.getId());
            throw new UnauthorizedAccessException("You do not have permission to create this post.");
        }

        try {
            Post createdPost = postRepo.save(post);

            log.info("Post with id {} created under project {} by user {} successfully.", createdPost.getId(), project.getId(), user.getId());
            return createdPost;
        } catch (DataIntegrityViolationException e) {
            log.error("Error creating post under project {}: {}" , project.getId(), e.getMessage());
            throw new BadArgumentException("Invalid data");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while creating post.");
        }
    }

    public Post updatePost(Long id, Post post, User user) {
        try {
            if (!postRepo.existsById(id)) {
                log.error("Post with id {} does not exist.", id);
                throw new BadArgumentException("Post does not exist.");
            }

            Post originalPost = getPostById(id);
            if (!originalPost.getProject().getUser().getId().equals(user.getId())) {
                log.error("User with id {} does not have permission to modify this post.", user.getId());
                throw new UnauthorizedAccessException("You do not have permission to modify this post.");
            }

            originalPost.setTitle(post.getTitle());
            originalPost.setImage(post.getImage());
            originalPost.setText(post.getText());

            Post updatedPost = postRepo.save(originalPost);

            log.info("Post with id: {} updated successfully.", id);
            return updatedPost;
        } catch (DataIntegrityViolationException e) {
            log.error("Error modifying post {}: {}", id, e.getMessage());
            throw new BadArgumentException("Invalid data");
        } catch (UnauthorizedAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw e;
        }
    }

    public void deletePost(Long id, User user) {
        if (!postRepo.existsById(id)) {
            log.error("Post with id {} does not exist.", id);
            throw new BadArgumentException("Post does not exist.");
        }

        Post originalPost = getPostById(id);
        if (!originalPost.getProject().getUser().getId().equals(user.getId())) {
            log.error("User with id {} does not have permission to delete this post.", user.getId());
            throw new UnauthorizedAccessException("You do not have permission to delete this post.");
        }

        postRepo.deleteById(id);
        log.info("Post with id: {} deleted successfully.", id);
    }
}

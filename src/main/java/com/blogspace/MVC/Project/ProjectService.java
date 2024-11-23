package com.blogspace.MVC.Project;


import com.blogspace.MVC.Post.Post;
import com.blogspace.MVC.User.User;
import com.blogspace.util.exception.BadArgumentException;
import com.blogspace.util.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepo projectRepo;

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public List<Post> getAllPostsFromProjectById(Long id) {
        return getProjectById(id).getPosts();
    }

    public Project getProjectById(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new BadArgumentException("Project with id " + id + " does not exist."));
    }

    public Project createProject(Project project, User user) {
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(user);

        try {
            Project createdProject = projectRepo.save(project);

            log.info("Project with id: {} created by user {} successfully.", createdProject.getId(), user.getId());
            return createdProject;
        } catch (DataIntegrityViolationException e) {
            log.error("Error creating project: {}", e.getMessage());
            throw new BadArgumentException("Invalid data.");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while creating the project.");
        }
    }

    public Project updateProject(Long id, User user, Project project) throws UnauthorizedAccessException {
        try {
            if (!projectRepo.existsById(id)) {
                log.error("Project with id: {} does not exist.", id);
                throw new BadArgumentException("Project does not exist.");
            }

            Project originalProject = getProjectById(id);
            if (!user.getId().equals(id)) {
                log.error("User with id {} is not the creator of project with id {}.", user.getId(), id);
                throw new UnauthorizedAccessException("User is not the creator of this project.");
            }

            originalProject.setTitle(project.getTitle());
            originalProject.setDescription(project.getDescription());
            originalProject.setImage(project.getImage());

            Project updatedProject = projectRepo.save(originalProject);

            log.info("Project with id: {} updated successfully.", id);
            return updatedProject;
        } catch (DataIntegrityViolationException e) {
            log.error("Error updating project: {}", e.getMessage());
            throw new BadArgumentException("Invalid data.");
        } catch (BadArgumentException | UnauthorizedAccessException e) {
            throw e;
        } catch
        (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw e;
        }
    }

    public void deleteProject(Long id, User user) throws UnauthorizedAccessException {
        if (!projectRepo.existsById(id)) {
            log.error("Project with id: {} does not exist.", id);
            throw new BadArgumentException("Project with id: " + id + " does not exist.");
        }

        Project originalProject = getProjectById(id);
        if (!Objects.equals(user.getId(), originalProject.getUser().getId())) {
            log.error("User with id {} is not the creator of project with id {}.", user.getId(), id);
            throw new UnauthorizedAccessException("User is not the creator of this project.");
        }

        projectRepo.deleteById(id);
        log.info("Project with id: {} deleted successfully.", id);
    }
}

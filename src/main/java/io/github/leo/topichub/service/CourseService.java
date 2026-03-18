package io.github.leo.topichub.service;

import io.github.leo.topichub.domain.model.Category;
import io.github.leo.topichub.domain.model.Course;
import io.github.leo.topichub.dto.request.CreateCourseRequest;
import io.github.leo.topichub.dto.request.UpdateCourseRequest;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.exception.ConflictException;
import io.github.leo.topichub.exception.ResourceNotFoundException;
import io.github.leo.topichub.repository.CategoryRepository;
import io.github.leo.topichub.repository.CourseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseService(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    public CreateCourseResponse createCourse(CreateCourseRequest request) {

        List<Category> categories = categoryRepository.findAllById(request.categoryIds());

        if (categories.isEmpty() || categories.size() != request.categoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories not found");
        }

        if (courseRepository.existsByNameIgnoreCase(request.name())) {
            throw new ConflictException("Course with name " + request.name() + " already exists");
        }

        Course course = new Course();
        course.setName(request.name());
        course.setCategoryIds(request.categoryIds());

        var savedCourse = courseRepository.save(course);

        return new CreateCourseResponse(
                savedCourse.getId(), savedCourse.getName(), savedCourse.getCreatedAt(), savedCourse.getCategoryIds());
    }

    public PageResponse<CoursesListResponse> listAllCourses(Pageable pp) {

        var page = courseRepository
                .findAll(pp)
                .map(co -> new CoursesListResponse(co.getId(), co.getName(), co.getCreatedAt(), co.getCategoryIds()));

        return PageResponse.from(page);
    }

    public UpdateCourseResponse updateCourse(String id, UpdateCourseRequest request) {
        var course = courseRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));

        course.setName(request.name());

        if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
            course.setCategoryIds(request.categoryIds());
        }

        var savedCourse = courseRepository.save(course);

        return new UpdateCourseResponse(
                savedCourse.getId(), savedCourse.getName(), savedCourse.getCreatedAt(), savedCourse.getCategoryIds());
    }

    public void archiveCourse(String id) {

        var course = courseRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.deactivate();
        courseRepository.save(course);
    }

    public CourseDetailsResponse courseDetails(String id) {

        var course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return new CourseDetailsResponse(
                course.getId(), course.getName(), course.getCreatedAt(), course.isActive(), course.getCategoryIds());
    }
}

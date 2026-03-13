package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateCourseRequest;
import io.github.leo.topichub.dto.request.UpdateCourseRequest;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Courses")
@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest request) {
        var course = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @GetMapping()
    public ResponseEntity<PageResponse<CoursesListResponse>> listCourses(
            @PageableDefault(sort = {"name"}) Pageable pp) {

        var page = courseService.listAllCategories(pp);

        return ResponseEntity.ok(page);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<UpdateCourseResponse> updateCourse(
            @PathVariable String id, @RequestBody @Valid UpdateCourseRequest dto) {

        var updatedCourse = courseService.updateCourse(id, dto);

        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {

        courseService.archiveCourse(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsResponse> showCourseDetails(@PathVariable String id) {

        var course = courseService.courseDetails(id);

        return ResponseEntity.ok(course);
    }
}

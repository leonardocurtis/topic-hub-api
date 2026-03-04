package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateCourseRequest;
import io.github.leo.topichub.dto.response.CoursesListResponse;
import io.github.leo.topichub.dto.response.CreateCourseResponse;
import io.github.leo.topichub.dto.response.PageResponse;
import io.github.leo.topichub.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest request) {
        var course = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CoursesListResponse>> listCourses(
            @PageableDefault(sort = {"name"}) Pageable pp) {

        var page = courseService.listAllCategories(pp);

        return ResponseEntity.ok(page);
    }
}

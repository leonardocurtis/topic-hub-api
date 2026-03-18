package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateCourseRequest;
import io.github.leo.topichub.dto.request.UpdateCourseRequest;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Courses", description = "Endpoints responsible for managing courses available in the forum.")
@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Create a new course", description = """
        Creates a new course.

        Courses can be associated with topics in the forum
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Course created successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateCourseResponse.class)))
    })
    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest request) {
        var course = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @Operation(summary = "List all courses", description = """
        Returns a paginated list of available courses

        Courses are sorted by **name** by default
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Courses retrieved successfully",
                content =
                        @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<PageResponse<CoursesListResponse>> listCourses(
            @PageableDefault(sort = {"name"}) Pageable pp) {

        var page = courseService.listAllCourses(pp);

        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Update course", description = "Updates course information such as name or description")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Course updated successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UpdateCourseResponse.class)))
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<UpdateCourseResponse> updateCourse(
            @Parameter(description = "Unique identifier of the course") @PathVariable String id,
            @RequestBody @Valid UpdateCourseRequest dto) {

        var updatedCourse = courseService.updateCourse(id, dto);

        return ResponseEntity.ok(updatedCourse);
    }

    @Operation(summary = "Archive course", description = """
        Archives (deactivates) a course.

        Archived courses cannot be associated with new topics.

        Permissions:
        - ADMIN
        """)
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Course archived successfully")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "Unique identifier of the course") @PathVariable String id) {

        courseService.archiveCourse(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get course details", description = "Returns detailed information about a specific course")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Course found",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CourseDetailsResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsResponse> showCourseDetails(
            @Parameter(description = "Unique identifier of the course") @PathVariable String id) {

        var course = courseService.courseDetails(id);

        return ResponseEntity.ok(course);
    }
}

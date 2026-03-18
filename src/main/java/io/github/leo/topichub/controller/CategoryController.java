package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateCategoryRequest;
import io.github.leo.topichub.dto.request.UpdateCategoryRequest;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.service.CategoryService;
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

@Tag(
        name = "Categories",
        description = "Endpoints for managing forum categories. Categories organize topics into logical groups")
@RestController
@RequestMapping("/categories")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category", description = "Creates a new forum category")
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Category created successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateCategoryResponse.class)))
    })
    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<CreateCategoryResponse> addCategory(@RequestBody @Valid CreateCategoryRequest request) {

        var category = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = "List all categories", description = """
        Returns a paginated list of all categories.

        Categories are sorted by **name** by default.
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Categories retrieved successfully",
                content =
                        @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<PageResponse<CategoriesListResponse>> listCategories(
            @PageableDefault(sort = {"name"}) Pageable pp) {

        var page = categoryService.listAllCategories(pp);

        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Archive category", description = """
        Deactivates (archives) a category.

        Archived categories are no longer available for new topics.

        Permissions:
        - ADMIN
        """)
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Category archived successfully")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateCategory(
            @Parameter(description = "Unique identifier of the category") @PathVariable String id) {

        categoryService.archiveCategory(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update category", description = "Updates category information such as name")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Category updated successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UpdateCategoryResponse.class)))
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(
            @Parameter(description = "Unique identifier of the category") @PathVariable String id,
            @RequestBody @Valid UpdateCategoryRequest request) {

        var updatedCategory = categoryService.updateCategory(id, request);

        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Get category details", description = "Returns detailed information about a specific category")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Category found",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CategoryDetailsResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> showCategoryDetails(
            @Parameter(description = "Unique identifier of the category") @PathVariable String id) {
        var category = categoryService.categoryDetails(id);

        return ResponseEntity.ok(category);
    }
}

package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateCategoryRequest;
import io.github.leo.topichub.dto.request.UpdateCategoryRequest;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categories")
@RestController
@RequestMapping("/categories")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<CreateCategoryResponse> addCategory(@RequestBody @Valid CreateCategoryRequest request) {

        var category = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping()
    public ResponseEntity<PageResponse<CategoriesListResponse>> listCategories(
            @PageableDefault(sort = {"name"}) Pageable pp) {

        var page = categoryService.listAllCategories(pp);

        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateCategory(@PathVariable String id) {

        categoryService.archiveCategory(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(
            @PathVariable String id, @RequestBody @Valid UpdateCategoryRequest request) {

        var updatedCategory = categoryService.updateCategory(id, request);

        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> showCategoryDetails(@PathVariable String id) {
        var category = categoryService.categoryDetails(id);

        return ResponseEntity.ok(category);
    }
}

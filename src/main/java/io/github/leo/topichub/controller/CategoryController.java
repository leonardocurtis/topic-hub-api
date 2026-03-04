package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.CreateCategoryRequest;
import io.github.leo.topichub.dto.response.CategoriesListResponse;
import io.github.leo.topichub.dto.response.CreateCategoryResponse;
import io.github.leo.topichub.dto.response.PageResponse;
import io.github.leo.topichub.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}

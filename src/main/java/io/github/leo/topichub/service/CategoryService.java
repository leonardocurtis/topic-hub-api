package io.github.leo.topichub.service;

import io.github.leo.topichub.domain.model.Category;
import io.github.leo.topichub.dto.request.CreateCategoryRequest;
import io.github.leo.topichub.dto.request.UpdateCategoryRequest;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.exception.ConflictException;
import io.github.leo.topichub.exception.ResourceNotFoundException;
import io.github.leo.topichub.repository.CategoryRepository;
import io.github.leo.topichub.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public CategoryService(CategoryRepository categoryRepository, CourseRepository courseRepository) {
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

    private static CreateCategoryResponse capitalize(Category category) {

        String formatted = category.getName().substring(0, 1).toUpperCase()
                + category.getName().substring(1);

        return new CreateCategoryResponse(category.getId(), formatted, category.getCreatedAt(), category.isActive());
    }

    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.name().toLowerCase().trim())) {
            throw new ConflictException("Category already registered");
        }

        var category = new Category();
        category.setName(request.name().toLowerCase().trim());

        var savedCategory = categoryRepository.save(category);

        return capitalize(savedCategory);
    }

    public PageResponse<CategoriesListResponse> listAllCategories(Pageable pp) {

        var page = categoryRepository.findAll(pp).map(c -> new CategoriesListResponse(c.getId(), c.getName()));

        return PageResponse.from(page);
    }

    public void archiveCategory(String id) {

        if (courseRepository.existsByCategoryIdsContaining(id)) {
            throw new ConflictException("Cannot delete category that is used by courses");
        }

        var category = categoryRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.deactivate();
        categoryRepository.save(category);
    }

    public UpdateCategoryResponse updateCategory(String id, @Valid UpdateCategoryRequest request) {

        var category = categoryRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(request.name());

        var savedCategory = categoryRepository.save(category);

        return new UpdateCategoryResponse(savedCategory.getId(), savedCategory.getName());
    }

    public CategoryDetailsResponse categoryDetails(String id) {

        var category = categoryRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return new CategoryDetailsResponse(category.getId(), category.getName(), category.isActive());
    }
}

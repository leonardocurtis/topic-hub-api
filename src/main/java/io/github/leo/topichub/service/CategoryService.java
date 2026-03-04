package io.github.leo.topichub.service;

import io.github.leo.topichub.domain.model.Category;
import io.github.leo.topichub.dto.request.CreateCategoryRequest;
import io.github.leo.topichub.dto.response.CategoriesListResponse;
import io.github.leo.topichub.dto.response.CreateCategoryResponse;
import io.github.leo.topichub.dto.response.PageResponse;
import io.github.leo.topichub.exception.ConflictException;
import io.github.leo.topichub.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private static CreateCategoryResponse capitalize(Category category) {

        String formatted = category.getName().substring(0, 1).toUpperCase()
                + category.getName().substring(1);

        return new CreateCategoryResponse(category.getId(), formatted);
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
}

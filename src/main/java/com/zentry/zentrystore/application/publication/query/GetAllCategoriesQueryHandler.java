package com.zentry.zentrystore.application.publication.query;

import com.zentry.zentrystore.application.publication.dto.CategoryDTO;
import com.zentry.zentrystore.application.publication.mapper.CategoryMapper;
import com.zentry.zentrystore.domain.publication.model.Category;
import com.zentry.zentrystore.domain.publication.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetAllCategoriesQueryHandler {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public GetAllCategoriesQueryHandler(CategoryRepository categoryRepository,
                                        CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> handle(GetAllCategoriesQuery query) {
        List<Category> categories = categoryRepository.findActiveTopLevelCategories();

        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
package com.zentry.zentrystore.application.publication.query;


import com.zentry.zentrystore.application.publication.dto.CategoryDTO;
import com.zentry.zentrystore.domain.publication.model.Category;
import com.zentry.zentrystore.domain.publication.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetAllCategoriesQueryHandler {

    private final CategoryRepository categoryRepository;

    public GetAllCategoriesQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> handle(GetAllCategoriesQuery query) {
        List<Category> categories = categoryRepository.findActiveTopLevelCategories();

        // TODO: Mapear a DTOs cuando tengamos mapper
        return null;
    }
}
package com.zentry.zentrystore.application.publication.mapper;

import com.zentry.zentrystore.application.publication.dto.CategoryDTO;
import com.zentry.zentrystore.domain.publication.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setIconUrl(category.getIconUrl());
        dto.setActive(category.getActive());
        dto.setDisplayOrder(category.getDisplayOrder());

        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
            dto.setParentName(category.getParent().getName());
        }

        return dto;
    }
}
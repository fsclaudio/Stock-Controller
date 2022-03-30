package com.stock.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.stock.dto.CategoryDTO;
import com.stock.dto.CategoryFormDTO;

public interface CategoryService {

	CategoryDTO save(CategoryFormDTO body);

	Page<CategoryDTO> listCategories(PageRequest pageRequest);

	CategoryDTO updateCategory(Long id, CategoryFormDTO body);

	CategoryDTO findById(Long id);

	void deleteCategory(Long id);
}

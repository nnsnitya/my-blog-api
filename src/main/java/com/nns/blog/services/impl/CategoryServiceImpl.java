package com.nns.blog.services.impl;

import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.entities.Category;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.mappers.CategoryMapper;
import com.nns.blog.repositories.CategoryRepository;
import com.nns.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private CategoryMapper categoryMapper;

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.categoryTitle());
        category.setCategoryDesc(categoryDto.categoryDescription());
        Category updatedCat = categoryRepo.save(category);
        return categoryMapper.toDto(updatedCat);
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
        return categoryMapper.toDto(cat);
    }

    @Cacheable(value = "categories", key = "'all_cat'")
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream().map(cat -> categoryMapper.toDto(cat)).collect(Collectors.toList());
        return catDtos;
    }

}

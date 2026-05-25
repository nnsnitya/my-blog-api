package com.nns.blog.services.impl;

import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.entities.Category;
import com.nns.blog.exceptions.ResourceNotFoundException;
import com.nns.blog.repositories.CategoryRepository;
import com.nns.blog.services.CategoryService;
import com.nns.blog.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = Mapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepo.save(category);
        return Mapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.categoryTitle());
        category.setCategoryDesc(categoryDto.categoryDescription());
        Category updatedCat = categoryRepo.save(category);
        return Mapper.mapToCategoryDto(updatedCat);
    }

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
        return Mapper.mapToCategoryDto(cat);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream().map(cat -> Mapper.mapToCategoryDto(cat)).collect(Collectors.toList());
        return catDtos;
    }

}

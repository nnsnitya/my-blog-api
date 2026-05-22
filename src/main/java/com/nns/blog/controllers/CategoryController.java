package com.nns.blog.controllers;

import com.nns.blog.constants.ApiConstants;
import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.ResponseHandler;
import com.nns.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.VERSION1+"/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDto catDto) {
        CategoryDto category = categoryService.createCategory(catDto);
        return ResponseHandler.generateResp("Creating Category", HttpStatus.CREATED, category, Code.SUCCESS.getCode());
    }

    //update
    @PutMapping("/{catId}")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDto catDto,
                                                 @PathVariable Long catId) {
        CategoryDto categoryDto = categoryService.updateCategory(catDto, catId);
        return ResponseHandler.generateResp("Update Category", HttpStatus.OK, categoryDto, Code.SUCCESS.getCode());
    }

    //delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
        return ResponseHandler.generateResp("Category is Deleted", HttpStatus.OK, null, Code.SUCCESS.getCode());
    }

    //get
    @GetMapping("/{catId}")
    public ResponseEntity<Object> getCategory(@PathVariable Long catId) {
        CategoryDto category = categoryService.getCategory(catId);
        return ResponseHandler.generateResp("Category", HttpStatus.OK, category, Code.SUCCESS.getCode());
    }

    //getAll
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        return ResponseHandler.generateResp("All Categories", HttpStatus.OK, categoryDtos, Code.SUCCESS.getCode());
    }
}

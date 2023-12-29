package com.codewithvikas.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvikas.blog.payload.CategoryDto;
import com.codewithvikas.blog.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(
		name = "CRUD REST APIs for Category Resource"
	)
public class CategoryController {
	
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Operation(
			summary = "Create Category REST API",
			description = "Create Category REST API is used to add a new category in the database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	// Build add category REST API...
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get Category By Id REST API",
			description = "Get Category By Id REST API is used to get a single category from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	// Build Get Category REST API...
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	@Operation(
			summary = "Get All Categories REST API",
			description = "Get All Categories REST API is used to fetch all the categories from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	// Build Get All Categories REST API...
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@Operation(
			summary = "Update Category REST API",
			description = "Update Category REST API is used to update a specific category in the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	// Build Update Category REST API...
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long categoryId,
													  @RequestBody CategoryDto categoryDto){
		
		CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
		
		return ResponseEntity.ok(updatedCategory);
	}
	
	@Operation(
			summary = "Delete Category REST API",
			description = "Delete Category REST API is used to delete a specific category from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	// Build Delete Category REST API...
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully!");
	}
}

package com.codewithvikas.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithvikas.blog.entity.Category;
import com.codewithvikas.blog.exception.ResourceNotFoundException;
import com.codewithvikas.blog.payload.CategoryDto;
import com.codewithvikas.blog.repository.CategoryRepository;
import com.codewithvikas.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository,
							   ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
									.collect(Collectors.toList());
	}

	@Override
	public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		Category updatedCategory = categoryRepository.save(category);
		
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		categoryRepository.delete(category);
	}

}

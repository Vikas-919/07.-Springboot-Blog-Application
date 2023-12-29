package com.codewithvikas.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvikas.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}

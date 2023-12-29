package com.codewithvikas.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvikas.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByCategoryId(Long categoryId);

}

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvikas.blog.payload.PostDto;
import com.codewithvikas.blog.payload.PostResponse;
import com.codewithvikas.blog.service.PostService;
import com.codewithvikas.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
		name = "CRUD REST APIs for Post Resource"
	)
public class PostController {

	private PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@Operation(
			summary = "Get All Posts REST API",
			description = "Get All Posts REST API is used to fetch all the posts from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	// Read all posts REST API...
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(name="pageNo", defaultValue = AppConstants.DEAFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(name="pageSize", defaultValue = AppConstants.DEAFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(name="sortBy", defaultValue = AppConstants.DEAFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(name="sortDir", defaultValue = AppConstants.DEAFAULT_SORT_DIRECTION, required = false) String sortDir 
			){
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	@Operation(
			summary = "Get Post By Id REST API",
			description = "Get Post By Id REST API is used to get a single post from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	// Read single post by Id REST API...
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@Operation(
			summary = "Create Post REST API",
			description = "Create Post REST API is used to save post into database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	// Creating a new post REST API...
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Update Post REST API",
			description = "Update Post REST API is used to update a specific post in the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	// Updating an existing post by Id REST API...
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
											  @PathVariable("id") long id){
		return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Post REST API",
			description = "Delete Post REST API is used to delete a specific post from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	// Deleting an existing post by Id REST API...
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id){
		postService.deletePostBtId(id);
		return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
	}
	
	// Build Get Posts by Category REST API...
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
		List<PostDto> posts = postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(posts);
	}
	
	
}

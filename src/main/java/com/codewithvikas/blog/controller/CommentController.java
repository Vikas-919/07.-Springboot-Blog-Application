package com.codewithvikas.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvikas.blog.payload.CommentDto;
import com.codewithvikas.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(
		name = "CRUD REST APIs for Comment Resource"
	)
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Operation(
			summary = "Create Comment REST API",
			description = "Create Comment REST API is used to save a comment against a specific post in the database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name="postId") long postId,
												    @Valid @RequestBody CommentDto commentDto) {
		
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get All Comments REST API",
			description = "Get All Comments REST API is used to fetch all the comments against a specific post from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(name="postId") long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@Operation(
			summary = "Get Comment By Id REST API",
			description = "Get Comment By Id REST API is used to get a specific comment from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(name="postId") long postId,
													 @PathVariable(name="commentId") long commentId){
	
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Update Comment REST API",
			description = "Update Comment REST API is used to update a specific comment in the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(name="postId") long postId,
													@PathVariable(name="commentId") long commentId,
													@Valid @RequestBody CommentDto commentDto){
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Comment REST API",
			description = "Delete Comment REST API is used to delete a specific comment from the database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name = "Bear Authentication"
	)
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(name="postId") long postId,
						 @PathVariable(name="commentId") long commentId) {
		
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully!", HttpStatus.OK);
	}
}

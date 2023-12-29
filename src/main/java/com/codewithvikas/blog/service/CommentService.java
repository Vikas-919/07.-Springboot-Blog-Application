package com.codewithvikas.blog.service;

import java.util.List;

import com.codewithvikas.blog.payload.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);
	List<CommentDto> getCommentsByPostId(long postId);
	CommentDto getCommentById(long postId, long commentId);
	CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
	void deleteComment(long postId, long commentId);
}

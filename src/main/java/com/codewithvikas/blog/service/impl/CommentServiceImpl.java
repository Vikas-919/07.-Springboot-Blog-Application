package com.codewithvikas.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codewithvikas.blog.entity.Comment;
import com.codewithvikas.blog.entity.Post;
import com.codewithvikas.blog.exception.BlogAPIException;
import com.codewithvikas.blog.exception.ResourceNotFoundException;
import com.codewithvikas.blog.payload.CommentDto;
import com.codewithvikas.blog.repository.CommentRepository;
import com.codewithvikas.blog.repository.PostRepository;
import com.codewithvikas.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository,
							  PostRepository postRepository,
							  ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}
	
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
	}
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		
		// Retrieve post entity by post id...
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		// Set post to Comment entity...
		comment.setPost(post);
		
		// Save comment entity to DB
		Comment savedComment = commentRepository.save(comment);
		
		return mapToDto(savedComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// Retrieve comments by post id...
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		// Converting list of Comment entities to list of CommentDto's..
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// Retrieve post entity by post id...
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		// Retrieve comment entity by comment id
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
		}
		
		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		// Retrieve post entity by post id...
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
			
		// Retrieve comment entity by comment id
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
		}
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		return mapToDto(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		// Retrieve post entity by post id...
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
					
		// Retrieve comment entity by comment id
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id", commentId));
				
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
		}
		
		commentRepository.delete(comment);;
		
	}

}

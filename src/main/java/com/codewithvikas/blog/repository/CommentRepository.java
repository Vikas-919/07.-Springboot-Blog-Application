package com.codewithvikas.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvikas.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// query method for getting all comments by post id...
	List<Comment> findByPostId(long postId);
}

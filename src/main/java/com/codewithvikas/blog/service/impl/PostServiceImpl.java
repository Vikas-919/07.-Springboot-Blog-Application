package com.codewithvikas.blog.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithvikas.blog.entity.Category;
import com.codewithvikas.blog.entity.Post;
import com.codewithvikas.blog.exception.ResourceNotFoundException;
import com.codewithvikas.blog.payload.PostDto;
import com.codewithvikas.blog.payload.PostResponse;
import com.codewithvikas.blog.repository.CategoryRepository;
import com.codewithvikas.blog.repository.PostRepository;
import com.codewithvikas.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository,
						   ModelMapper mapper,
						   CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}
	
	//convert Entity into DTO
	private PostDto mapToDTO(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		return postDto;
	}
	
	//convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		
		//convert DTO into Entity...
		Post post = mapToEntity(postDto);
		
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		
		//convert entity to DTO...
		PostDto postResponseDto = mapToDTO(savedPost);
		
		return postResponseDto;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		//create pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		// get content from Page object (Page class has getContent() method)....
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content = listOfPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		//get post by id from database...
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		return mapToDTO(updatedPost);
	}

	@Override
	public void deletePostBtId(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		return posts.stream().map((post) -> mapToDTO(post))
				.collect(Collectors.toList());		
	}

}

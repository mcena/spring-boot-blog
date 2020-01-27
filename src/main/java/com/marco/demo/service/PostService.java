package com.marco.demo.service;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.marco.demo.dto.PostDto;
import com.marco.demo.exception.PostNotFoundException;
import com.marco.demo.model.Post;
import com.marco.demo.repository.PostRepository;

//POST MANAGER (handles the creation of posts)

@Service
public class PostService {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private PostRepository postRepository;
	
	public List<PostDto> showAllPosts() {
		
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
	}
	
	public void createPost(PostDto postDto)
	{
		Post post = mapFromDtoToPost(postDto);
		postRepository.save(post);
		
	}
	private PostDto mapFromPostToDto(Post post)
	{
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getUsername());
		return postDto;
		
	}
	private Post mapFromDtoToPost(PostDto postDto)
	{
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User not found"));
		post.setCreatedOn(Instant.now());
		post.setUsername(loggedInUser.getUsername());
		post.setUpdatedOn(Instant.now());
		return post;
	}
	
	public PostDto readSinglePost(Long id)
	{
		Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("For id " + id));
		return mapFromPostToDto(post);
	}
	

	
}

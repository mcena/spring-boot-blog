package com.marco.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marco.demo.model.Post;


public interface PostRepository extends JpaRepository<Post,Long>{

}

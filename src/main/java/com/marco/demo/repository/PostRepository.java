package com.marco.demo.repository;

//REPOSITORY OF POSTS (implemented JpaRepository)
//reduces the amount of boilerplate codes in terms in SQL queries


import org.springframework.data.jpa.repository.JpaRepository;

import com.marco.demo.model.Post;


public interface PostRepository extends JpaRepository<Post,Long>{

}

package com.example.SpringSecurityProject.Dao;

import com.example.SpringSecurityProject.Models.BlogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

public interface postsDao extends JpaRepository<BlogModel, Long> {
    @Query(value = "SELECT * FROM blog_model WHERE 1", nativeQuery = true)
    List<BlogModel> findAllPosts();

    @Query(value = "insert into BlogModel username = :username, content = :content, postTime = :postTime", nativeQuery = true)
    BlogModel addPost(@Param(value="username") String username,
                      @Param(value = "content") String content,
                      @Param(value = "postTime") Date postTime);
}

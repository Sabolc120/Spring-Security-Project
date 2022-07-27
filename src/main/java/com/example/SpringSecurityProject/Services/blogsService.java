package com.example.SpringSecurityProject.Services;

import com.example.SpringSecurityProject.Models.BlogModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface blogsService {
    List<BlogModel> getBlogs();

    BlogModel addPost(String username, String content, Date postTime);
}

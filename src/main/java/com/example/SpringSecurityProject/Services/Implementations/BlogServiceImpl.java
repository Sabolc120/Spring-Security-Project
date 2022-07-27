package com.example.SpringSecurityProject.Services.Implementations;

import com.example.SpringSecurityProject.Dao.postsDao;
import com.example.SpringSecurityProject.Models.BlogModel;
import com.example.SpringSecurityProject.Services.blogsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements blogsService {

    private postsDao postsDao;

    @Override
    public List<BlogModel> getBlogs() {
        return postsDao.findAllPosts();
    }

    @Override
    public BlogModel addPost(String username, String content, Date postTime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        BlogModel blog = new BlogModel();
        blog.setBlogPost(content);
        blog.setPostDate(dateFormat.format(date));
        blog.setUsername(authentication.getName());
        return postsDao.save(blog);
    }

    public BlogModel addPos(String username, String content, Date postTime) {
        return postsDao.addPost(username, content, postTime);
    }
}

package com.example.SpringSecurityProject.Controllers;

import com.example.SpringSecurityProject.Dao.postsDao;
import com.example.SpringSecurityProject.Models.BlogModel;
import com.example.SpringSecurityProject.Services.blogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    public blogsService SERVICE;

    @Autowired
    public com.example.SpringSecurityProject.Dao.postsDao postsDao;

    @GetMapping("/blog")
    private ModelAndView blogGet(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return new ModelAndView("redirect:/login");
        }
        else{
            ModelAndView mv = new ModelAndView("blog.html");
            List<BlogModel> blogs = SERVICE.getBlogs();
            mv.addObject("blogs", blogs);
            return mv;
        }
    }
    @GetMapping("/postBlog")
    private ModelAndView blogWriteGet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authority = String.valueOf(authentication.getAuthorities());
        System.out.println(authority);
        return new ModelAndView("postBlog.html");
    }
    @PostMapping("/postBlog")
    public ModelAndView blogPost(@RequestParam(value = "username", required = false) String username,
                                 @RequestParam("content") String content,
                                 Date postTime){
        BlogModel blog = SERVICE.addPost(username, content, postTime);
        return new ModelAndView("redirect:/blog");
    }
    @GetMapping("/torles/{id}")
    public ModelAndView deleteBlog(@PathVariable Long id){
        postsDao.deleteById(id);
        return new ModelAndView("redirect:/blog");
    }
}

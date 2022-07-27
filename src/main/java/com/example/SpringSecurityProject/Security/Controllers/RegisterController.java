package com.example.SpringSecurityProject.Security.Controllers;
import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import com.example.SpringSecurityProject.Security.Entities.VerificationToken;
import com.example.SpringSecurityProject.Security.Events.RegisterCompleteEvent;
import com.example.SpringSecurityProject.Security.Repositories.UserRepo;
import com.example.SpringSecurityProject.Security.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ApplicationEventPublisher publisher;

    //Registering Phase
    @GetMapping("/register")
    public ModelAndView registerGet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("register.html");
        }
        return new ModelAndView("alreadyRegistered.html");
    }
    @PostMapping("/register")
    public ModelAndView registerPost(UserEntity user, final HttpServletRequest request){
        //Gather data
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        String username = user.getUsername();


        //Save data
        UserEntity user1 = userService.registerUser(firstName, lastName, email, password, username);
        userRepo.save(user1);

        //Event occur, for verification
        publisher.publishEvent(new RegisterCompleteEvent(user1,applicationUrl(request)));
        ModelAndView mv = new ModelAndView("success");
        //Verify token re sending
        return mv;
    }
    //Verification Phase
    @GetMapping("/verifyReg")
    public ModelAndView verifyRegGet(@RequestParam("token") String token){
        String result = String.valueOf(userService.validateVerification(token));
        if(result.equalsIgnoreCase("valid")){
            return new ModelAndView("verificationS");
        }
        else{
            return new ModelAndView("error");
        }
    }
    //Resend token phase
    @GetMapping("/resendToken")
    public ModelAndView resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        UserEntity user = verificationToken.getUser();
        resendVerificationTokenConsole(user, applicationUrl(request), verificationToken);
        ModelAndView mv = new ModelAndView("resendToken.html");
        return mv;
    }
    private void resendVerificationTokenConsole(UserEntity user, String applicationUrl, VerificationToken verificationToken){
        String url = applicationUrl+"/verifyReg?token=" + verificationToken.getToken();
        log.info("Click the re-sent link to verify your account: {}", url);
    }
    //Creating url
    private String applicationUrl(HttpServletRequest httpServletRequest){
        return "http://"+
                httpServletRequest.getServerName() +
                ":"+
                httpServletRequest.getServerPort() +
                httpServletRequest.getContextPath();
    }
    //Login phase
    @GetMapping("/login")
    private ModelAndView loginGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("login.html");
        }
        return new ModelAndView("alreadyLoggedIn.html");
    }
}

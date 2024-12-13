package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class maincontroller {
     @Autowired
    private UserService userService;

    @RequestMapping(value = "/home")
    public String homePage(){
        return "home";
    }

    @RequestMapping(value = "/about")
    public String aboutPage(){
        return "about";
    }

    @RequestMapping(value = "/services")
    public String servicePage(){
        return "services";
    }

    @RequestMapping(value = "/contact")
    public String contactPage(){
        return "contact";
    }

    @RequestMapping(value = "/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/signup")
    public String signupPage(@ModelAttribute UserForm userForm )
    {
      
        return "signup";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String processSignUp(@Valid @ModelAttribute UserForm userForm,BindingResult br,HttpSession session){

        System.out.println(userForm);

        if(br.hasErrors()){
            return "signup";
        } 

        // User user=User.builder()
        // .username(userForm.getName())
        // .password(userForm.getPassword())
        // .email(userForm.getEmail())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber()).build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);

       

        
         userService.saveUser(user);

        //message : "Registration successfull"
         session.setAttribute("message", "Registration Successfull");
        
     
        
        
        
      
        
        return "redirect:/signup";
    }
}

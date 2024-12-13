package com.scm.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.services.UserService;



//using security after /user/htmlpages you can secure user own data

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    public  UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);


   
   

    //user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    //user profile page 
    @RequestMapping(value="/profile")
    public String userProfile(){
        return "user/profile";
    }

   
}

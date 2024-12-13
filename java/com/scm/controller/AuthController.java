package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.dao.UserRepo;
import com.scm.entities.User;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;



     @RequestMapping("/verify/{emailToken}")
   public String verifyemail(@PathVariable String emailToken){

    User user = userRepo.findByEmailToken(emailToken).orElse(null);
    if(user!=null){
       
            user.setEmailVerified(true);
            user.setEnabled(true);
            userRepo.save(user);

       

            return "success_page";
    
       
    }    
    return "error_page";


   }

  
}

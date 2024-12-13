package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Loggindinuser;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {
    Logger logger = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void loggedinUserInformation(Model model, Authentication authentication){
        if(authentication==null){
            return;
        }
        String username=Loggindinuser.getEmailofloginuser(authentication);
       logger.info("username is :"+username);

       User user=userService.getUserByEmail(username);
        model.addAttribute("loggedinUser", user);

    }
}

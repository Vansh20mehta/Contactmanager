package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Loggindinuser {

    public static String getEmailofloginuser(Authentication authentication){

    
    if(authentication instanceof OAuth2AuthenticationToken){
      var  OAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
                String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

                 DefaultOAuth2User oauth2user = (DefaultOAuth2User) authentication.getPrincipal();

                 String username="";

                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                 username=oauth2user.getAttribute("email").toString();
    }

                else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    username=oauth2user.getAttribute("email") != null ? 
                    oauth2user.getAttribute("email").toString() : oauth2user.getAttribute("login").toString()+"@github.com";
                }
            
            return username;

             } else{
                
               return authentication.getName();
             }

              
               

              

                
}


    public static String getLinkForEmailVerification(String emailToken){

      return "http://localhost:8080/auth/verify/"+emailToken;
    }
}

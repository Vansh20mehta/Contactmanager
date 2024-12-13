package com.scm.servicesimpl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.scm.dao.UserRepo;
import com.scm.entities.Providers;
import com.scm.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SecurityUserDetailService implements UserDetailsService,AuthenticationSuccessHandler{

    @Autowired
    private UserRepo userRepo;

     

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"+username));
    }

   


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                    //identify the provider
                 var  OAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
                String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

                DefaultOAuth2User oauth2user = (DefaultOAuth2User) authentication.getPrincipal();

                User user=new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setRoles(List.of("ROLE_USER"));
               
               

                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    //google attributes
                    user.setProvider(Providers.GOOGLE);
                    user.setName(oauth2user.getAttribute("name").toString());
                    user.setEmail(oauth2user.getAttribute("email").toString());
                    user.setProfilePic(oauth2user.getAttribute("picture").toString());
                }

                else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    //github attributes
                    String email=oauth2user.getAttribute("email") != null ? 
                     oauth2user.getAttribute("email").toString() : oauth2user.getAttribute("login").toString()+"@github.com";
                    
                    user.setEmail(email);
                    user.setProvider(Providers.GITHUB);
                    user.setName(oauth2user.getAttribute("login").toString());
                    user.setProfilePic(oauth2user.getAttribute("avatar_url").toString());

                 }

                

                 User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
                 if(user2==null){
                     userRepo.save(user);
                 }

                 new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
 }

 
    
    
}

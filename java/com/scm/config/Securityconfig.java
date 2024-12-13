package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.servicesimpl.SecurityUserDetailService;

@Configuration
public class Securityconfig {
    //user create and login using memory service
    
    // @Bean
    // //userDetailService is use for match user credentials in database and login page 
    // public UserDetailsService userDetailsService(){

    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()
    //             .username("admin")
    //             .password("admin123")
    //              .roles("ADMIN","USER")
    //              .build();

    //     UserDetails user2 = User
    //     .withDefaultPasswordEncoder()
    //     .username("user")
    //     .password("user123")
    //     .build();

    //     //this interface is use for check user details in memeory
    //     InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
// }  
        @Autowired    
        private SecurityUserDetailService securityUserDetailService;

        @Autowired
        private AuthFailureHandler authFailureHandler;

    //config of authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //userDetailService object 
        daoAuthenticationProvider.setUserDetailsService(securityUserDetailService);
        //password encoder object 
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        //Url configuration -public or private
        httpSecurity.authorizeHttpRequests(authorize->{
        //authorize.requestMatchers("/home","/signup").permitAll();
        authorize.requestMatchers("user/**").authenticated();
        
        authorize.anyRequest().permitAll();
      
    });

    //form default login
    //form login can be modified as per need
    httpSecurity.formLogin(formLogin->{
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
         formLogin.successForwardUrl("/user/profile");

        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");

        formLogin.failureHandler(authFailureHandler);
    });

    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.logout(logoutForm->{
        logoutForm.logoutUrl("/dologout");
        logoutForm.logoutSuccessUrl("/login?logout=true");
    });

    httpSecurity.oauth2Login(oauth->{
        oauth.loginPage("/login");
        oauth.successHandler(securityUserDetailService);
        
        
    }); 

        return httpSecurity.build();
    }
}

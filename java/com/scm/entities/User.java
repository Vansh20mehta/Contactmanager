 package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//userdetails interface is use for fetch user data in database
 public class User implements UserDetails {
    @Id
    private String userId;
    @Column(nullable = false)
    private String name;
    @Email()
    private String email;
    private String password;
    @Column(length = 300)
    private String about;
    @Column(length = 300)
    private String profilePic;
    private String phoneNumber;

    //information
    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    //SELF,GOOGLE,GITHUB
    //this annotation is use for set default login provider in database
    @Enumerated(value = EnumType.STRING)
    private Providers provider=Providers.SELF;
    private Providers providerId;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Contact> contacts=new ArrayList<>();

    //use for set multiple roles of user in network routes
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles=new ArrayList<>();

  private String emailToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //simple granted authorities is use for grant access of personal data for user
        //list of roles[USER,ADMIN] 
        //collection of SimpleGrantedAuthority[roles{ADMIN,USER}]
        List<SimpleGrantedAuthority> collect = roles.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return collect;
    }

    
    @Override
    public String getUsername() {
        //email field is consider username in login
                return this.email;       
    } 

    @Override
    public boolean isAccountNonExpired() {
        return true;
         }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

  

  

    @Override
    public boolean  isEnabled(){
        return this.enabled;
    }


    @Override
    public boolean isCredentialsNonExpired() {
    return true;   
    }


    


}
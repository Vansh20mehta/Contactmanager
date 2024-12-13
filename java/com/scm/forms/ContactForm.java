package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

    @NotBlank(message = "not empty")
    private String username;
    
    @NotBlank(message = "email is not empty")
    private String email;
 
    @NotBlank(message = "phone number is not empty")
    private String phoneNumber;
     
    private String address;
    private String description;

    private MultipartFile contactImage;
   
    private boolean favorite=false;
   
    private String socialLink;
    
 private String picture;
}
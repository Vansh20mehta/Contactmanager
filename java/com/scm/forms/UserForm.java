package com.scm.forms;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    
    @NotBlank(message = "name can't be empty")
    @Size(min=3,max = 20,message = "size must be in between 3 to 20")
    private String name;
   
    
    @Id
    @NotBlank(message = "email is not like this ")
    private String email;
    @NotBlank(message = "can't be empty")
    private String password;
    
    private String about;
    @Size(min = 10,max = 10,message = "Invalid")
    private String phoneNumber;

}

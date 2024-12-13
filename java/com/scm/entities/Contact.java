package com.scm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber; 
    private String address;
    private String picture;
    private String description;
   
    private boolean favorite=false;
    
    private String socialLink;

    @ManyToOne
    @JsonIgnore
    private User user;
}

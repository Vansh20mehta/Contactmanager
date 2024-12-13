package com.scm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contact;
import com.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
  public ContactService contactService;

  @GetMapping("/contact/{contactid}")
public Contact getContact(@PathVariable String contactid){
 
  System.out.println(contactid);
    return contactService.getContactById(contactid);
}


    
}
 
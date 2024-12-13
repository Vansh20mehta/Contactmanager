package com.scm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    public Contact saveContact(Contact contact);

    public Contact getContactById(String id);

    public Contact updateContact(Contact contact);

    public void delete(String id);

    

    public List<Contact> getAllContact();

    public List<Contact> getByUserId(String userId);

    public Page<Contact> getByUser(User user,int page,int size,String sortby,String direction);

    public Page<Contact> searchByName(User user,String namekeyword,int page,int size,String sortby,String direction) ;

    public Page<Contact> searchByEmail(User user,String emailkeyword,int page,int size,String sortby,String direction);

    public Page<Contact> searchByPhone(User user,String Phonekeyword,int page,int size,String sortby,String direction);


   

    
}

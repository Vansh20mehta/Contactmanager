package com.scm.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.scm.dao.ContactRepo;
import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.services.ContactService;
import com.scm.services.UserService;
import com.scm.services.imageService;

@Service
public class ContactServiceimpl implements ContactService{
    @Autowired
    private ContactRepo contactRepo;

    @Autowired
   private  UserService userService;

    @Override
    public Contact saveContact(Contact contact) {
       String contactId=UUID.randomUUID().toString();
       contact.setId(contactId);

       
      
       return contactRepo.save(contact);
    }

    @Override
    public Contact getContactById(String id) {
      
         return contactRepo.findById(id).orElse(null);
    }

    @Override
    public Contact updateContact(Contact contact) {
      Contact Oldcontact = contactRepo.findById(contact.getId()).orElse(null);
        Oldcontact.setName(contact.getName());
        Oldcontact.setEmail(contact.getEmail());
        Oldcontact.setAddress(contact.getAddress());
        Oldcontact.setDescription(contact.getDescription());
        Oldcontact.setFavorite(contact.isFavorite());
        Oldcontact.setPhoneNumber(contact.getPhoneNumber());
        Oldcontact.setPicture(contact.getPicture());
        Oldcontact.setSocialLink(contact.getSocialLink());
         
        return contactRepo.save(Oldcontact);
         
        
    }

    @Override
    public void delete(String id) {
       var contact = contactRepo.findById(id).orElse(null);
        this.contactRepo.delete(contact);
     }

   

    @Override
    public List<Contact> getAllContact() {
        List<Contact> contactlist = contactRepo.findAll();
      
       return contactlist;
    }

    @Override
  public List<Contact> getByUserId(String userId) {
     return contactRepo.findByUserId(userId);
   }

   @Override
   public Page<Contact> getByUser(User user,int page,int size,String sortby,String direction) {
      Sort sort=direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
    Pageable pageable=PageRequest.of(page, size,sort);
     return contactRepo.findByUser(user,pageable);
   }

  

@Override
public Page<Contact> searchByName(User user,String namekeyword, int page, int size, String sortby, String direction) {
   Sort sort=direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
   Pageable pageable=PageRequest.of(page, size,sort);
   return contactRepo.findByUserAndNameContaining(user,namekeyword, pageable);
}

@Override
public Page<Contact> searchByEmail(User user,String emailkeyword, int page, int size, String sortby, String direction) {
   Sort sort=direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
    Pageable pageable=PageRequest.of(page, size,sort);
    return contactRepo.findByUserAndEmailContaining( user,emailkeyword, pageable);
   
   }

@Override
public Page<Contact> searchByPhone(User user,String phonekeyword, int page, int size, String sortby, String direction) {
   Sort sort=direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
    Pageable pageable=PageRequest.of(page, size,sort);
    return contactRepo.findByUserAndPhoneNumberContaining(user, phonekeyword, pageable);
   }







   

   

   

}

package com.scm.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.Loggindinuser;
import com.scm.services.ContactService;
import com.scm.services.UserService;
import com.scm.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user/contacts")
public class ContactController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private imageService imageService;

    @Autowired
    private UserService userService;

    // add contact page
    @RequestMapping("/add")
    public String addContact(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute(contactForm);
        return "user/add-contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult br, HttpSession session,
            Authentication authentication) {

        if (br.hasErrors()) {
            br.getAllErrors().forEach(error -> logger.info(error.toString()));
            return "user/add-contact";
        }

        String username = Loggindinuser.getEmailofloginuser(authentication);
        User user = userService.getUserByEmail(username);

        String fileURL = imageService.uploadImage(contactForm.getContactImage());

        Contact contact = new Contact();
        contact.setName(contactForm.getUsername());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setPicture(fileURL);
        contact.setDescription(contactForm.getDescription());
        contact.setSocialLink(contactForm.getSocialLink());
        contact.setFavorite(contactForm.isFavorite());
        contact.setUser(user);

        contactService.saveContact(contact);

        session.setAttribute("message", "Contact Add Succesfully");

        return "redirect:/user/contacts/add";
    }

    @RequestMapping()
    public String Contactview(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortby", defaultValue = "name") String sortby,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,

            Model model, Authentication authentication) {
        String username = Loggindinuser.getEmailofloginuser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getByUser(user, page, size, sortby, direction);
        model.addAttribute("pageContacts", pageContacts);

        ContactSearchForm contactSearchForm = new ContactSearchForm();
        model.addAttribute("contactSearchForm", contactSearchForm);

        return "user/contacts";
    }

    // search handler
    @RequestMapping(value = "/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortby", defaultValue = "name") String sortby,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {

        String username = Loggindinuser.getEmailofloginuser(authentication);

        User user = userService.getUserByEmail(username);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        Page<Contact> pageContacts = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContacts = contactService.searchByName(user, contactSearchForm.getKeyword(), page, size, sortby,
                    direction);
        }

        else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContacts = contactService.searchByEmail(user, contactSearchForm.getKeyword(), page, size, sortby,
                    direction);
        }

        else if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContacts = contactService.searchByPhone(user, contactSearchForm.getKeyword(), page, size, sortby,
                    direction);
        }

        else if (contactSearchForm.getField().equalsIgnoreCase("null")) {
            pageContacts = contactService.getByUser(user, page, size, sortby, direction);
        }

        else {
            pageContacts = contactService.searchByName(user, contactSearchForm.getKeyword(), page, size, sortby,
                    direction);
        }

        model.addAttribute("pageContacts", pageContacts);
        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deletecontact(@PathVariable("contactId") String contactId) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        return "redirect:/user/contacts";
    }

    @RequestMapping("/update/{contactId}")
    public String updatecontact(@PathVariable("contactId") String contactId, Model model) {
        Contact contact = new Contact();
        contact = contactService.getContactById(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setUsername(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());

        contactForm.setPicture(contact.getPicture());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setSocialLink(contact.getSocialLink());
        

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/updateView";
    }

    @RequestMapping(value = "/updateprocess/{contactId}",method = RequestMethod.POST)
      public String updateProcess(@ModelAttribute ContactForm contactForm, @PathVariable("contactId") String contactId,Model model){
        Contact contact=new Contact();
                 contact = contactService.getContactById(contactId);

                 
                  contact.setId(contactId);
                 contact.setName(contactForm.getUsername());
                 contact.setEmail(contactForm.getEmail());
                 contact.setPhoneNumber(contactForm.getPhoneNumber());
                 contact.setAddress(contactForm.getAddress());
                
                 contact.setDescription(contactForm.getDescription());
                 contact.setSocialLink(contactForm.getSocialLink());
                 contact.setFavorite(contactForm.isFavorite());

                 String uploadImage = imageService.uploadImage(contactForm.getContactImage());
                 contact.setPicture(uploadImage);


               
        contactService.updateContact(contact);
          
              
        return "redirect:/user/contacts";
      }

}

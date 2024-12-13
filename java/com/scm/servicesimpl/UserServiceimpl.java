package com.scm.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.scm.dao.UserRepo;
import com.scm.entities.User;
import com.scm.helper.Loggindinuser;
import com.scm.services.EmailService;
import com.scm.services.UserService;

@Service
public class UserServiceimpl implements UserService{

  @Autowired
  private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //userid :have to generate 
        String userId=UUID.randomUUID().toString();
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserId(userId);

       

        String emailToken=UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
       String emailLink=Loggindinuser.getLinkForEmailVerification(emailToken);
       User savedUser = userRepo.save(user);
       emailService.sendEmail(savedUser.getEmail(), "Verify email :email", emailLink);
    
        
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
    return userRepo.findById(id);    
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2=userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceAccessException("User not found"));
        user2.setName(user2.getUsername());
        user2.setEmail(user2.getEmail());
        user2.setPassword(user2.getPassword());
        user2.setPhoneNumber(user2.getPhoneNumber());
        user2.setAbout(user2.getAbout());
        user2.setProfilePic(user2.getProfilePic());
        user2.setEmailVerified(user2.isEmailVerified());
        user2.setEnabled(user2.isEnabled());
        user2.setPhoneVerified(user2.isPhoneVerified());
        user2.setProvider(user2.getProvider());
        user2.setProviderId(user2.getProviderId());

       User u= userRepo.save(user2);
        return Optional.ofNullable(u);
    }

    @Override
    public void deleteUser(String id) {
        User user2=userRepo.findById(id).orElseThrow(()-> new ResourceAccessException("User not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2=userRepo.findById(userId).orElse(null);   
        return user2 !=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user2=userRepo.findByEmail(email).orElse(null);
        return user2 !=null ? true : false;
     }

    @Override
    public List<User> getAllUser() {
       return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
       
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User getByEmailToken(String emialToken) {
            return userRepo.findByEmailToken(emialToken).orElse(null);
    }


    

}

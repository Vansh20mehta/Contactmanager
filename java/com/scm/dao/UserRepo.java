package com.scm.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;



@Repository
public interface UserRepo extends JpaRepository<User,String> {
    // databse methods
    // custom finder methods
   Optional<User> findByEmail(String email);
   
   Optional<User> findByEmailToken(String emailToken);


}

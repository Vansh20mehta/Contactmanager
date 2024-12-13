package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {


 public User saveUser(User user);

    public Optional<User> getUserById(String id);

    public Optional<User> updateUser(User user);

    public void deleteUser(String id);

    public boolean isUserExist(String userId);

    public boolean isUserExistByEmail(String email);

    public List<User> getAllUser();

    public User getUserByEmail(String email);

    public User getByEmailToken(String emialToken);
}

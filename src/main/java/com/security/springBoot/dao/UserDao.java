package com.security.springBoot.dao;


import com.security.springBoot.models.Role;
import com.security.springBoot.models.User;

import java.util.List;

public interface UserDao {
    User getUserByEmail(String email);
    User getUserByName(String name);
    public void addUser(User user);
    public void deleteUser(long id);
    public void updateUser(User user);
    public List<User> userList();
    public User getUserById(long id);
    public List<Role> roles();
    public Role getRoleById(Long id);
}

package com.security.springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.security.springBoot.dao.UserDao;
import com.security.springBoot.models.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        if(user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return user;
    }


}

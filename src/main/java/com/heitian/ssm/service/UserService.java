package com.heitian.ssm.service;

import com.heitian.ssm.model.User;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User getUserByName(String userName);
    void updateUser(User user);
}

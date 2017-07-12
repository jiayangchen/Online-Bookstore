package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UserDao;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public User getUserByName(String userName) {
        return userDao.selectUserByName(userName);
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user.getSex(),user.getAddress(),user.getPhone(),user.getIsDelete(),
                user.getRid(),user.getuName());
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
}

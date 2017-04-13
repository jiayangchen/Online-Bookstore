package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UserDao;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    @CacheEvict(value = {"getAllUser"}, allEntries = true)
    public User getUserByName(String userName) {
        return userDao.selectUserByName(userName);
    }

    @Cacheable("getAllUser")
    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }
}

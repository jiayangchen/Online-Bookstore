package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UserRoleDao;
import com.heitian.ssm.model.UserRole;
import com.heitian.ssm.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleDao userRoleDao;

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public List<UserRole> getAllUserRoles() {
        return userRoleDao.selectAllUserRoles();
    }
}

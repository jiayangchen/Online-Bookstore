package com.heitian.ssm.dao;

import com.heitian.ssm.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao {
    List<UserRole> selectAllUserRoles();
}

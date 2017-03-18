package com.heitian.ssm.dao;

import com.heitian.ssm.model.User;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User selectUserByName(@Param("userName") String userName);
    List<User> selectAllUser();
}

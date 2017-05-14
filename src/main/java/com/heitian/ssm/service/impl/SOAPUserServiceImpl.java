package com.heitian.ssm.service.impl;

import com.heitian.ssm.model.User;
import com.heitian.ssm.service.SOAPUserService;
import com.heitian.ssm.utils.WsConstants;

import javax.jws.WebService;

/**
 * Created by ChenJiayang on 2017/5/14.
 */

@WebService
public class SOAPUserServiceImpl implements SOAPUserService {
    @Override
    public String getUserName(String userId) {
        return "dongwq";
    }

    @Override
    public User getUser(String userId) {
        User user = new User();
        user.setRid(200L);
        user.setUid(200L);
        user.setuPassword("123");
        user.setuName("cjy");
        return user;
    }
}

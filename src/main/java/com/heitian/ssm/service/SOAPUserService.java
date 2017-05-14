package com.heitian.ssm.service;
import com.heitian.ssm.model.User;
import com.heitian.ssm.utils.WsConstants;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 * Created by ChenJiayang on 2017/5/14.
 */

@WebService
public interface SOAPUserService {
    @WebMethod
    public String getUserName(@WebParam(name = "userId")String userId);
    @WebMethod
    public User getUser(@WebParam(name = "userId")String userId);
}

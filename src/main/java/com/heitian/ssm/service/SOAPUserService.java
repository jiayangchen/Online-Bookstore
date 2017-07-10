package com.heitian.ssm.service;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.User;
import com.heitian.ssm.utils.WsConstants;
import org.json.JSONArray;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;


/**
 * Created by ChenJiayang on 2017/5/14.
 */

@WebService(targetNamespace="heitian")
@SOAPBinding(style = Style.RPC)
public interface SOAPUserService {
    String sayHi(String text);
    String searchBook(String bname);
}

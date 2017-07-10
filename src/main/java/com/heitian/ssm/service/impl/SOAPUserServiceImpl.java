package com.heitian.ssm.service.impl;
import com.heitian.ssm.dao.BookDao;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.SOAPUserService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/5/14.
 */

@WebService(endpointInterface = "com.heitian.ssm.service.SOAPUserService")
@SOAPBinding(style = Style.RPC)
public class SOAPUserServiceImpl implements SOAPUserService {

    @Resource
    BookDao bookDao;

    public String sayHi(@WebParam(name = "text") String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }

    @Override
    public String searchBook(@WebParam(name = "text") String bname) {
        JSONArray jsonArray = new JSONArray();
        List<Book> bookList = bookDao.selectAllBook();
        for(Book b : bookList){
            if(b.getbName().contains(bname)){
                JSONObject json = new JSONObject();
                json.put("bid",b.getBid());
                json.put("bName",b.getbName());
                json.put("bAuthor",b.getbAuthor());
                json.put("bPrice",b.getbPrice());
                json.put("bDisr",b.getbDiscr());
                json.put("bQuantity",b.getbQuantity());
                json.put("bCategory",b.getbCategory());
                jsonArray.put(json);
            }
        }
        return jsonArray.toString();
    }
}

package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/5/12.
 */

@Controller
@RequestMapping("/rest/getbook")
public class SearchRestController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value="/getBookById/{id}",method= RequestMethod.GET)
    public @ResponseBody
    String getUserById0(@PathVariable String id) {
        JSONArray jsonArray = new JSONArray();
        List<Book> bookList = bookService.getAllBook();
        for(Book b : bookList){
            if (b.getbName().contains(id)){
                JSONObject json = new JSONObject();
                json.put("bid",b.getBid());
                json.put("bName",b.getbName());
                json.put("bAuthor",b.getbAuthor());
                json.put("bPrice",b.getbPrice());
                json.put("bQuantity",b.getbQuantity());
                json.put("bCategory",b.getbCategory());
                json.put("bDiscr",b.getbDiscr());
                jsonArray.add(json);
            }
        }

        if(!jsonArray.isEmpty()){
            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put("SResult",jsonArray);*/
            return jsonArray.toString();
        }else{
            return null;
        }
    }
}

package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSONArray;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.utils.MyHttpHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ChenJiayang on 2017/5/12.
 */

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class SearchRestController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value="/getBookByName",method= RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<JSONArray> getUserById0(@RequestParam("bname") String id)
    {
        HttpHeaders headers = MyHttpHeader.getHttpHeaders();
        JSONArray jsonArray = new JSONArray();
        List<Book> bookList = bookService.getAllBook();
        for(Book b : bookList)
        {
            if (b.getbName().contains(id))
            {
                jsonArray.add(b);
            }
        }
        return new ResponseEntity<>(jsonArray,headers,HttpStatus.OK);
    }


}

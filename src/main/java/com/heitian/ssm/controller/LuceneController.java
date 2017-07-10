package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.lucene.luceneIndex;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.LuceneSearchService;
import com.heitian.ssm.utils.MyHttpHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/6/19.
 */

@Controller
public class LuceneController {

    @Autowired
    private LuceneSearchService luceneSearchService;

    @RequestMapping(value="/luceneSearch")
    public String search(@RequestParam(value="target") String target,
                                            Model model) throws Exception {
        List<Book> target_booklist = luceneSearchService.luceneSearch(target);
        model.addAttribute("target_booklist",target_booklist);
        return "succ";
    }
}

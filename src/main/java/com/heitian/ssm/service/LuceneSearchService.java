package com.heitian.ssm.service;

import com.heitian.ssm.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJiayang on 2017/6/19.
 */

@Service
public interface LuceneSearchService {
    List<Book> luceneSearch(String tar) throws Exception;
    List<Book> luceneSearchCN(String tar) throws Exception;
}

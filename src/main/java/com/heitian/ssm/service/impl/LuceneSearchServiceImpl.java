package com.heitian.ssm.service.impl;

import com.heitian.ssm.lucene.luceneIndex;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.LuceneSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/6/19.
 */

@Service
public class LuceneSearchServiceImpl implements LuceneSearchService{

    @Resource
    private BookService bookService;

    @Override
    public List<Book> luceneSearch(String tar) throws Exception {
        List<Book> bookList = bookService.getAllBook();
        luceneIndex luceneIndex = new luceneIndex(bookList);
        return luceneIndex.search(tar);
    }

    @Override
    public List<Book> luceneSearchCN(String tar) throws Exception {
        List<Book> bookList = bookService.getAllBookCN();
        luceneIndex luceneIndex = new luceneIndex(bookList);
        return luceneIndex.search(tar);
    }
}

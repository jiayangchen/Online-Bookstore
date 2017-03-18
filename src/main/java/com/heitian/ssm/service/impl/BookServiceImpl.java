package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.BookDao;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService{
    @Resource
    private BookDao bookDao;

    public List<Book> getAllBook() {
        return bookDao.selectAllBook();
    }
}

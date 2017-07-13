package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.BookDao;
import com.heitian.ssm.dao.ProvidedDao;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.Provided;
import com.heitian.ssm.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService{
    @Resource
    private BookDao bookDao;
    @Resource
    private ProvidedDao providedDao;

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    //@Cacheable("getAllBook")
    public List<Book> getAllBook() {
        return bookDao.selectAllBook();
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    /*@Cacheable("getAllBookCN")*/
    public List<Book> getAllBookCN() {
        return bookDao.selectAllBookCN();
    }

    @Override
    public void updateBookStock(Long bid, int quan) {
        bookDao.updateBookStock(bid,quan);
    }

    @Override
    public void updateBookCNStock(Long bid, int quan) {
        bookDao.updateBookCNStock(bid,quan);
    }

    @Override
    public List<Book> getAllBookByCategory(String category) {
        return bookDao.selectAllBookByCategory(category);
    }

    @Override
    public List<Book> getAllBookCNByCategory(String category) {
        return bookDao.selectAllBookCNByCategory(category);
    }

    @Override
    public List<Provided> getBookIdByPId(long pid) {
        return providedDao.selectBIdByPId(pid);
    }

    @Override
    public void updateBookCN(Book book) {
        bookDao.updateBookCN(book);
    }

    @Override
    public Book getBookCNByBId(Long bid) {
        return bookDao.getBookCNByBId(bid);
    }

    @Override
    public Book getBookByBId(Long bid) {
        return bookDao.getBookByBId(bid);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }


}

package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.BookDao;
import com.heitian.ssm.dao.ProvidedDao;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.Page;
import com.heitian.ssm.model.Provided;
import com.heitian.ssm.service.BookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class,isolation= Isolation.DEFAULT)
public class BookServiceImpl implements BookService{

    @Resource
    private BookDao bookDao;
    @Resource
    private ProvidedDao providedDao;

    @Transactional(readOnly=true)
    public List<Book> getAllBook() {
        return bookDao.selectAllBook();
    }

    @Transactional(readOnly=true)
    public List<Book> getAllBookCN() {
        return bookDao.selectAllBookCN();
    }

    @Override
    @CacheEvict(value = { "getAllBook"}, allEntries = true)
    public void updateBookStock(Long bid, int quan) {
        bookDao.updateBookStock(bid,quan);
    }

    @Override
    @CacheEvict(value = { "getAllBookCN"}, allEntries = true)
    public void updateBookCNStock(Long bid, int quan) {
        bookDao.updateBookCNStock(bid,quan);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable("getAllBook")
    public List<Book> getAllBookByCategory(String category) {
        return bookDao.selectAllBookByCategory(category);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable("getAllBookCN")
    public List<Book> getAllBookCNByCategory(String category) {
        return bookDao.selectAllBookCNByCategory(category);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Provided> getBookIdByPId(long pid) {
        return providedDao.selectBIdByPId(pid);
    }

    @Override
    @CacheEvict(value = { "getAllBookCN"}, allEntries = true)
    public void updateBookCN(Book book) {
        bookDao.updateBookCN(book);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable("getAllBookCN")
    public Book getBookCNByBId(Long bid) {
        return bookDao.getBookCNByBId(bid);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable("getAllBook")
    public Book getBookByBId(Long bid) {
        return bookDao.getBookByBId(bid);
    }

    @Override
    @CacheEvict(value = { "getAllBook"}, allEntries = true)
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void showBookByPage (HttpServletRequest request, Model model, String langType) {
        String pageNow = request.getParameter("pageNow");
        Page page = null;
        List<Book> products = new ArrayList<>();
        int totalCount = (int) bookDao.getProductsCount();
        if (pageNow != null) {
            page = new Page(totalCount, Integer.parseInt(pageNow));
            if(langType.equals("zh")) {
                products = this.bookDao.selectBookCNByPage(page.getStartPos(), page.getPageSize());
            } else {
                products = this.bookDao.selectBookByPage(page.getStartPos(), page.getPageSize());
            }
        } else {
            page = new Page(totalCount, 1);
            if(langType.equals("zh")) {
                products = this.bookDao.selectBookCNByPage(page.getStartPos(), page.getPageSize());
            } else {
                products = this.bookDao.selectBookByPage(page.getStartPos(), page.getPageSize());
            }
        }

        model.addAttribute("bookList", products);
        model.addAttribute("page", page);
    }
}

package com.heitian.ssm.service;

import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.Provided;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BookService {
    List<Book> getAllBook();
    List<Book> getAllBookCN();

    List<Book> getAllBookByCategory(String category);
    List<Book> getAllBookCNByCategory(String category);

    void updateBookStock(Long bid, int quan);
    void updateBookCNStock(Long bid, int quan);

    List<Provided> getBookIdByPId(long pid);

    void updateBookCN(Book book);
    void updateBook(Book book);

    Book getBookCNByBId(Long bid);
    Book getBookByBId(Long bid);

    void showBookByPage(HttpServletRequest request, Model model,String langType);
}

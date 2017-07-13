package com.heitian.ssm.dao;

import com.heitian.ssm.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {

    List<Book> selectAllBook();
    List<Book> selectAllBookCN();

    List<Book> selectAllBookByCategory(String category);
    List<Book> selectAllBookCNByCategory(String category);

    void updateBookStock(Long bid,int quan);
    void updateBookCNStock(Long bid, int quan);

    void updateBookCN(Book book);
    void updateBook(Book book);
    Book getBookCNByBId(Long bid);
    Book getBookByBId(Long bid);
}

package com.heitian.ssm.service;

import com.heitian.ssm.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBook();
    List<Book> getAllBookCN();

    List<Book> getAllBookByCategory(String category);
    List<Book> getAllBookCNByCategory(String category);

    void updateBookStock(Long bid, int quan);
    void updateBookCNStock(Long bid, int quan);
}

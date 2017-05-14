package com.heitian.ssm.service;

import com.heitian.ssm.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBook();
    List<Book> getAllBookCN();
}

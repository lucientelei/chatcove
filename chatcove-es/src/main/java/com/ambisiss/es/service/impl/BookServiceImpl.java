package com.ambisiss.es.service.impl;

import com.ambisiss.es.pojo.Book;
import com.ambisiss.es.repo.BookRepository;
import com.ambisiss.es.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-6-1 18:59:12
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> searchBook(String keyWord) {
        return bookRepository.findByTitleOrAuthor(keyWord, keyWord);
    }

    @Override
    public SearchHits<Book> searchBook1(String keyWord) {
        return bookRepository.find(keyWord);
    }
}

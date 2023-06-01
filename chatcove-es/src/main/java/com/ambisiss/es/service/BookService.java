package com.ambisiss.es.service;

import com.ambisiss.es.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.security.PrivateKey;
import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-6-1 18:57:14
 */
public interface BookService {

    void addBook(Book book);

    List<Book> searchBook(String keyWord);

    SearchHits<Book> searchBook1(String keyWord);

}

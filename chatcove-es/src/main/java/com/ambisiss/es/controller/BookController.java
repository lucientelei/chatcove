package com.ambisiss.es.controller;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.es.pojo.Book;
import com.ambisiss.es.service.BookService;
import com.ambisiss.es.service.impl.BookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-6-1 19:02:50
 */
@RestController
@RequestMapping("/book")
@Api(tags = "ES图书测试",description = "BookController")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    @ApiOperation(value = "添加Book")
    public GlobalResult add(@RequestBody Book book) {
        bookService.addBook(book);
        return GlobalResult.success();
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索")
    public GlobalResult search(@RequestParam String keyWord) {
        List<Book> result = bookService.searchBook(keyWord);
        return GlobalResult.success(result);
    }

    @GetMapping("/search/hight")
    @ApiOperation(value = "搜索高亮返回")
    public GlobalResult searchHight(@RequestParam String keyWord) {
        SearchHits<Book> result = bookService.searchBook1(keyWord);
        return GlobalResult.success(result);
    }
}

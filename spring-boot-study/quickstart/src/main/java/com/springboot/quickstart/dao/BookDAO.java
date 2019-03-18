package com.springboot.quickstart.dao;

import com.springboot.quickstart.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {
    public List<Book> getBooks(){
        List<Book> books=new ArrayList<> (  );
        books.add ( new Book (1,"Spring Boot 实战",88.7) );
        books.add ( new Book (2,"Java 实战",56.3) );
        books.add ( new Book (3,"Vue.js 实战",44.7) );
            return books;
    }
}

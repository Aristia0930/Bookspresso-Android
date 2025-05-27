package com.ssafy.cafe.model.service;

import com.ssafy.cafe.model.dto.Book;
import com.ssafy.cafe.model.dto.BookRental;
import com.ssafy.cafe.model.dto.BookRentalInfo;

import java.util.Date;
import java.util.List;

public interface BookService {



    List<Book> getBookList();

    Book getBook(String isbn);

    int addRental(BookRental rental);

    List<BookRental> getRentalList(String userId);


    int returned(String rentalId);
    int returned2(String isbn);

    List<BookRentalInfo> getRentalInfo(String userId);
}

package com.ssafy.cafe.model.dao;

import com.ssafy.cafe.model.dto.Book;
import com.ssafy.cafe.model.dto.BookRental;
import com.ssafy.cafe.model.dto.BookRentalInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface BookDao {

    List<Book> getBookList();

    Book getBook(String isbn);

    int addRental(BookRental rental);

    List<BookRental> getRentalList(String userId);

    int returned(BookRental rental);

    BookRental getRental(String rentalId);

    BookRental getRental2(String isbn);

    int updateOverdueRentals();

    List<BookRentalInfo> getRentalInfo(String userId);

}

package com.ssafy.cafe.model.service;

import com.ssafy.cafe.model.dao.BookDao;
import com.ssafy.cafe.model.dto.Book;
import com.ssafy.cafe.model.dto.BookRental;
import com.ssafy.cafe.model.dto.BookRentalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;
    @Override
    public List<Book> getBookList() {
        return bookDao.getBookList();
    }

    @Override
    public Book getBook(String isbn) {
        return bookDao.getBook(isbn);
    }

    @Override
    public int addRental(BookRental rental) {
        return bookDao.addRental(rental);
    }

    @Override
    public List<BookRental> getRentalList(String userId) {
        return bookDao.getRentalList(userId);
    }

    @Override
    public int returned(String rentalId) {

        //일차적으로 조회해서 가져와서 수정
        BookRental rental = bookDao.getRental(rentalId);

        LocalDate today = LocalDate.now(); // 현재 날짜

        // 1. java.util.Date로 받아오기
        java.util.Date dueDateUtil = rental.getDueDate();

// 2. java.sql.Date로 변환
        java.sql.Date dueDateSql = new java.sql.Date(dueDateUtil.getTime());

// 3. LocalDate 변환
        LocalDate dueDate = dueDateSql.toLocalDate();

// 연체 일수 계산 (음수면 0일)
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
        overdueDays = Math.max(overdueDays, 0); // overdueDays가 음수일 경우 0으로 설정

// 연체료 계산: 예: 하루당 1000원
        int fee = (int) overdueDays * 1000;

        rental.setFee(rental.getFee()+fee);
        rental.setDueDate(Date.valueOf(today)); // 반납일을 오늘로
        rental.setStatus("returned");

        return bookDao.returned(rental);
    }

    @Override
    public int returned2(String isbn) {
        //일차적으로 조회해서 가져와서 수정
        BookRental rental = bookDao.getRental2(isbn);

        LocalDate today = LocalDate.now(); // 현재 날짜

        // 1. java.util.Date로 받아오기
        java.util.Date dueDateUtil = rental.getDueDate();

// 2. java.sql.Date로 변환
        java.sql.Date dueDateSql = new java.sql.Date(dueDateUtil.getTime());

// 3. LocalDate 변환
        LocalDate dueDate = dueDateSql.toLocalDate();

// 연체 일수 계산 (음수면 0일)
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
        overdueDays = Math.max(overdueDays, 0); // overdueDays가 음수일 경우 0으로 설정

// 연체료 계산: 예: 하루당 1000원
        int fee = (int) overdueDays * 1000;

        rental.setFee(rental.getFee()+fee);
        rental.setDueDate(Date.valueOf(today)); // 반납일을 오늘로
        System.out.println(rental.getStatus());
        if(rental.getStatus().equals("overdue")){
            rental.setStatus("overdueReturned");
        }else{
            rental.setStatus("returned");
        }

        System.out.println("status = " + rental.getStatus());
        System.out.println("dueDate = " + rental.getDueDate());
        System.out.println("fee = " + rental.getFee());
        System.out.println("rentalId = " + rental.getRentalId());

        return bookDao.returned(rental);
    }

    @Override
    public List<BookRentalInfo> getRentalInfo(String userId) {

        return bookDao.getRentalInfo(userId);
    }

}

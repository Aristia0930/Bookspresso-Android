package com.ssafy.cafe.controller.rest;


import com.ssafy.cafe.model.dto.BookRental;
import com.ssafy.cafe.model.dto.BookRentalInfo;
import com.ssafy.cafe.model.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Book;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/book")
public class BookRestController {

    @Autowired
    private BookService bookService;

    //책전체 조회

    @GetMapping("")
    @Operation(summary="책전체 조회",
            description = "")
    public ResponseEntity<List<Book>> getBookList(){
        List<Book> list = bookService.getBookList();

        return ResponseEntity.ok(list); 
    }

    @GetMapping("/{isbn}")
    @Operation(summary="책단건 조회",
            description = "isbn 값을 이용하여 책을 단건 조회한다")
    public ResponseEntity<Book> getBook(@PathVariable String isbn){
        Book book=bookService.getBook(isbn);

        return ResponseEntity.ok(book);
    }

    //책 대여
    @PostMapping("/rental")
    @Operation(summary="책대여",
            description = "BookRental dot 값을 이용하여 db 에 값을 추가")
    public  ResponseEntity<Boolean> addRental(@RequestBody BookRental bookRental){
        BookRental rental = new BookRental();
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(1);

        rental.setRentalDate(Date.valueOf(today));
        rental.setDueDate(Date.valueOf(due));

        // 필요 시 나머지 필드(userId, isbn 등)도 여기서 세팅해야 DB 저장 가능
        rental.setUserId(bookRental.getUserId());
        rental.setIsbn(bookRental.getIsbn());
        rental.setFee(bookRental.getFee());
        rental.setStatus("rented");

        int result = bookService.addRental(rental);
        if(result==1){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.badRequest().build();
        }


    }

    //책 반납
    @PutMapping("/rental/{rentalId}")
    @Operation(summary="책반납",
            description = "rental_id 기반으로 반밥 상태로 수정")
    public  ResponseEntity<Boolean> addRental(@PathVariable String rentalId){
        int result = bookService.returned(rentalId);
        if(result==1){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.badRequest().build();
        }

    }

    //책 반납 isbn
    @PutMapping("/rental2/{isbn}")
    @Operation(summary="책반납",
            description = "isbn 기반으로 반밥 상태로 수정")
    public  ResponseEntity<Boolean> addRental2(@PathVariable String isbn){
        int result = bookService.returned2(isbn);
        if(result==1){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.badRequest().build();
        }

    }

    //유저 대역 내역 조회
    @GetMapping("/rental/{userId}")
    @Operation(summary="유저아이디로 대여내역조회",
            description = "유저아이디로 값을 이용하여 책을 대여 내역 조회한다")
    public ResponseEntity<List<BookRental>> getRentalList(@PathVariable String userId){
        List<BookRental> list=bookService.getRentalList(userId);

        return ResponseEntity.ok(list);
    }

    //유저 대역 내역 조회
    @GetMapping("/rental/list/{userId}")
    @Operation(summary="유저아이디로 대여내역조회",
            description = "유저아이디로 값을 이용하여 책을 대여 내역과 책 내용을 함께 제공한다.")
    public ResponseEntity<List<BookRentalInfo>> getRentalInfo(@PathVariable String userId){
        List<BookRentalInfo> list=bookService.getRentalInfo(userId);

        return ResponseEntity.ok(list);
    }




}

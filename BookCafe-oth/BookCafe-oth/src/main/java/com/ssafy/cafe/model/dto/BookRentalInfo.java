package com.ssafy.cafe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRentalInfo {
    private Integer rentalId;
    private String userId;
    private String isbn;
    private Date rentalDate;
    private Date dueDate;
    private Integer fee;
    private String status;
    private  Book book;
}

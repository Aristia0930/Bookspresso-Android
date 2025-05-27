package com.ssafy.cafe.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "도서 정보 DTO")
@Data
@AllArgsConstructor  // 모든 필드를 받는 생성자
@NoArgsConstructor   // 기본 생성자
public class Book {
    @Schema(description = "도서 ISBN (Primary Key)", example = "978-89-01-12345-6")
    private String isbn;

    @Schema(description = "도서 제목", example = "자바의 정석")
    private String title;

    @Schema(description = "저자", example = "남궁성")
    private String author;

    @Schema(description = "도서 요약", example = "자바 기초부터 고급까지 설명한 책")
    private String summary;

    @Schema(description = "도서 상태 (available, borrowed, reserved 등)", example = "available")
    private String status;

    @Schema(description = "도서 이미지 URL", example = "https://example.com/book.jpg")
    private String img;



}

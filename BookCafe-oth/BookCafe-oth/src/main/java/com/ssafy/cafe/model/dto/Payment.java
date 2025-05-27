package com.ssafy.cafe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private Long id;
    private String tid;
    private String userId;
    private String orderId;
    private String itemName;
    private int totalAmount;
    private String status; // "READY", "APPROVED"
    private LocalDateTime createdAt;
}

package com.ssafy.cafe.kakaoPay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoPaymentOrderRequest {
    private String cid;    // 가맹점 코드, 10자 (필수)
    private String tid;    // 결제 고유번호, 20자 (필수)
}

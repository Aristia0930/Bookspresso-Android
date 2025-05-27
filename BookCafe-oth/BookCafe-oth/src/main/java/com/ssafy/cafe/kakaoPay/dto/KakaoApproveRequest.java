package com.ssafy.cafe.kakaoPay.dto;


import lombok.Data;

@Data
public class KakaoApproveRequest {
    private String cid;                  // 가맹점 코드
    private String cid_secret;          // 가맹점 코드 인증키 (선택)
    private String tid;                 // 결제 고유번호
    private String partner_order_id;    // 가맹점 주문번호
    private String partner_user_id;     // 가맹점 회원 ID
    private String pg_token;            // 결제 인증 토큰
    private String payload;             // 저장하고 싶은 값 (선택)
    private Integer total_amount;       // 총 결제 금액 (선택)
}

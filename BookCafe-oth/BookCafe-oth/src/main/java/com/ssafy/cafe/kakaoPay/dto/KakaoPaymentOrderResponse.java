package com.ssafy.cafe.kakaoPay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoPaymentOrderResponse {
    private String tid;                  // 결제 고유번호
    private String cid;                  // 가맹점 코드
    private String status;               // 결제 상태
    private String partner_order_id;    // 가맹점 주문번호
    private String partner_user_id;     // 가맹점 회원 id
    private String payment_method_type; // 결제 수단

    private String item_name;            // 상품 이름
    private String item_code;            // 상품 코드
    private Integer quantity;            // 상품 수량

    private Amount amount;               // 결제 금액
    private CanceledAmount canceled_amount; // 취소된 금액
    private CancelAvailableAmount cancel_available_amount; // 취소 가능 금액

    private LocalDateTime created_at;   // 결제 준비 요청 시각
    private LocalDateTime approved_at;  // 결제 승인 시각
    private LocalDateTime canceled_at;  // 결제 취소 시각

    private SelectedCardInfo selected_card_info; // 결제 카드 정보

    private List<PaymentActionDetail> payment_action_details; // 결제/취소 상세

    // 내부 DTO들
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Amount {
        private Integer total;
        private Integer tax_free;
        private Integer vat;
        private Integer point;
        private Integer discount;
        private Integer green_deposit;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CanceledAmount {
        private Integer total;
        private Integer tax_free;
        private Integer vat;
        private Integer point;
        private Integer discount;
        private Integer green_deposit;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CancelAvailableAmount {
        private Integer total;
        private Integer tax_free;
        private Integer vat;
        private Integer point;
        private Integer discount;
        private Integer green_deposit;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SelectedCardInfo {
        private String card_bin;
        private Integer install_month;
        private String installment_type;
        private String card_corp_name;
        private String interest_free_install;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentActionDetail {
        private String aid;
        private LocalDateTime approved_at;
        private Integer amount;
        private Integer point_amount;
        private Integer discount_amount;
        private Integer green_deposit;
        private String payment_action_type;
        private String payment_method_type;
        private String payload;
    }
}

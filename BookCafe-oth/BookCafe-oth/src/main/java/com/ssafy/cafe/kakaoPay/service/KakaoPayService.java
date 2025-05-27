package com.ssafy.cafe.kakaoPay.service;

import com.ssafy.cafe.kakaoPay.dto.*;
import com.ssafy.cafe.model.dao.PaymentDao;
import com.ssafy.cafe.model.dto.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@Slf4j
public class KakaoPayService {
    private final String secretKey;
    private KakaoPayReadyResponse kakaoReady;
    private KakaoReadyRequest latestReadyRequest;

    @Autowired
    private PaymentDao paymentDao;
    @Value("${kakaopay.cid}")
    private String cid;

    @Value("${my.uri}")
    private String uri;
    private final WebClient webClient;

    public KakaoPayService(@Value("${kakaopay.secretKey}") String secretKey) {
        this.secretKey = secretKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://open-api.kakaopay.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "SECRET_KEY " + secretKey)
                .build();
    }


    public KakaoPayReadyResponse readyToPay(KakaoReadyRequest requestDto) {
        Payment payment=new Payment();

        payment.setStatus("READY");
        payment.setUserId(requestDto.getPartner_user_id());
        payment.setOrderId(requestDto.getPartner_order_id());
        payment.setItemName(requestDto.getItem_name());
        payment.setTotalAmount(requestDto.getTotal_amount());
        payment.setCreatedAt(LocalDateTime.now());

        requestDto.setCid(cid);
//        requestDto.setApproval_url("http://localhost:9987/rest/payment/success");
//        requestDto.setCancel_url("http://localhost:9987/rest/payment/cancel");
//        requestDto.setFail_url("http://localhost:9987/rest/payment/fail");
        requestDto.setApproval_url("https://port-0-bookcafe-m5al105w4c7a25c2.sel4.cloudtype.app/rest/payment/success");
        requestDto.setCancel_url("https://port-0-bookcafe-m5al105w4c7a25c2.sel4.cloudtype.app/rest/payment/cancel");
        requestDto.setFail_url("https://port-0-bookcafe-m5al105w4c7a25c2.sel4.cloudtype.app/rest/payment/fail");


        try {
            kakaoReady = webClient.post()
                    .uri("/online/v1/payment/ready")
                    .bodyValue(requestDto)
                    .retrieve()
                    .bodyToMono(KakaoPayReadyResponse.class)
                    .block(); // 동기 호출

            if (kakaoReady == null || kakaoReady.getTid() == null) {
                throw new IllegalStateException("카카오페이 응답이 null이거나 tid가 없음");
            }

            payment.setTid(kakaoReady.getTid());
            latestReadyRequest = requestDto;


            paymentDao.insert(payment);

            return kakaoReady;

        } catch (Exception e) {
            // 로깅 및 에러 응답 처리
            log.error("카카오페이 결제 준비 중 예외 발생", e);
            throw new RuntimeException("카카오페이 결제 준비 실패", e);
        }

    }

    public KakaoApproveResponse approveResponse (String pgToken){
        KakaoApproveRequest requestDto= new KakaoApproveRequest();
        requestDto.setCid(cid);
        requestDto.setTid(kakaoReady.getTid());
        requestDto.setPartner_order_id(latestReadyRequest.getPartner_order_id());
        requestDto.setPartner_user_id(latestReadyRequest.getPartner_user_id());
        requestDto.setPg_token(pgToken);

        KakaoApproveResponse kakaoApproveResponse=new KakaoApproveResponse();
        try {
            kakaoApproveResponse=webClient.post()
                    .uri("/online/v1/payment/approve")
                    .bodyValue(requestDto)
                    .retrieve()
                    .bodyToMono(KakaoApproveResponse.class)
                    .block();

            if (kakaoApproveResponse == null || kakaoApproveResponse.getTid() == null) {
                throw new IllegalStateException("카카오페이 응답이 null이거나 tid가 없음");
            }
            paymentDao.update(kakaoApproveResponse.getTid(),"APPROVED");


            return kakaoApproveResponse;
        }catch (Exception e){
            log.error("카카오페이 성공 처리 중 예외 발생", e);
            throw new RuntimeException("카카오페이 결제 성공 처리 실패", e);
        }


    }

    public KakaoPaymentOrderResponse orderResponse (KakaoPaymentOrderRequest request){
        KakaoApproveRequest requestDto= new KakaoApproveRequest();
        requestDto.setCid(cid);
        requestDto.setTid(request.getTid());

        return webClient.post()
                .uri("/online/v1/payment/order")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(KakaoPaymentOrderResponse.class)
                .block();

    }


}

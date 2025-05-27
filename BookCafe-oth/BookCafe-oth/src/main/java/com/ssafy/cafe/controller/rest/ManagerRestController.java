package com.ssafy.cafe.controller.rest;

import com.ssafy.cafe.model.dto.OrderInfo;
import com.ssafy.cafe.model.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/manager")

@Tag(name = "manager controller", description = "관리자 기능을 정의한다.")

public class ManagerRestController {

    @Autowired
    private OrderService oService;

    //음식 주문 목록 물러오기 전체 조회 하도록 하자 날짜 상관없이
    //대기 상태면 N
    //수락이면 Y 로
    //거절이면 C 로 표시

    @GetMapping("")
    @Operation(summary="관리자 전체 주문 내역 리턴",
            description = "")
    public ResponseEntity<List<OrderInfo>> getOrder(){
        List<OrderInfo> orders=oService.getOrder();

        return ResponseEntity.ok(orders);
    }





    //수락 거절
    //주문번호와 NUM 을 넘겨받아서 수정한다.
    @PutMapping("/order/accecpt/{orderId}")
    @Operation(summary = "주문 상태 변경 (수락)", description = "주문 번호와 상태 코드를 받아 주문 상태를 변경합니다. 상태 코드는 'Y'(수락)")
    public ResponseEntity<OrderInfo> updateOrderYes(
            @PathVariable Long orderId) {


        int rs = oService.updateOrderStatus(orderId, "Y");
        if (rs == 1) {
            OrderInfo info=oService.getOrderInfo(Math.toIntExact(orderId));
            return ResponseEntity.ok(info);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/order/cancel/{orderId}")
    @Operation(summary = "주문 상태 변경 (거절)", description = "주문 번호와 상태 코드를 받아 주문 상태를 변경합니다. , 'C'(거절) 사용.")
    public ResponseEntity<OrderInfo> updateOrderStatus(
            @PathVariable Long orderId) {


        int rs = oService.updateOrderStatus(orderId, "C");
        if (rs == 1) {
            OrderInfo info=oService.getOrderInfo(Math.toIntExact(orderId));
            return ResponseEntity.ok(info);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }



}

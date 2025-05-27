package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetail;
import com.ssafy.cafe.model.dto.OrderInfo;
import com.ssafy.cafe.model.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/rest/order")
@CrossOrigin("*")
public class OrderRestController {
    @Autowired
    private OrderService oService;

    
    @PostMapping("")
    @Operation(summary="order dto를 넘기면 주문 넣기",
            description = "")
    public ResponseEntity<Integer> order(@RequestBody Order order){
    	
    	System.out.println(order.toString());
    	if (order.getDetails()==null || order.getDetails().size()==0) {
    		return ResponseEntity.ok(-1);
    	}
    	
    	int val=0;
    	for(OrderDetail detail:order.getDetails()) {
    		val+=detail.getQuantity();
    	}
    	if(val>15) {
    		return ResponseEntity.ok(-2);
    	}
    	
    	int oreder_id=oService.makeOrder(order);
    	return ResponseEntity.ok(oreder_id);
    }
    
    @GetMapping("/{orderId}")
    @Operation(summary="주문번호를 가지고 그 주문에 대한 상세 정보확인",
            description = "")
    public OrderInfo getOrderDetails(@PathVariable int orderId) {
    	
    	System.out.println(orderId);
    	OrderInfo order=oService.getOrderInfo(orderId);
    	
    	
    	return order;
    }



    
    @GetMapping("/byUser")
    @Operation(summary="{id}에 해당하는 사용자의 최근 1개월간 주문 내역을 반환한다."
            + "반환 정보는 1차 주문번호 내림차순, 2차 주문 상세 오름차순으로 정렬된다.", 
            description = "관통프로젝트 6단계(Android)에서 사용됨.")
    public List<OrderInfo> getLastMonthOrder(@RequestParam String id) {
        return oService.getLastMonthOrder(id);
    }    
    
    
    @GetMapping("/byUserIn6Months")
    @Operation(summary="{id}에 해당하는 사용자의 최근 6개월간 주문 내역을 반환한다."
            + "반환 정보는 1차 주문번호 내림차순, 2차 주문 상세 오름차순으로 정렬된다.", 
            description = "관통프로젝트 6단계(Android)에서 사용됨.")
    public List<OrderInfo> getLast6MonthOrder(@RequestParam String id) {
        return oService.getLast6MonthOrder(id);
    } 
	
}

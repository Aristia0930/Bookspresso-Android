package com.ssafy.cafe.model.service;

import java.util.List;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderInfo;

public interface OrderService {
    /**
     * 새로운 Order를 생성한다.
     * Order와 OrderDetail에 정보를 추가한다.
     * [심화]User 테이블에 사용자의 Stamp 개수를 업데이트 한다.
     * [심화]Stamp 테이블에 Stamp 이력을 추가한다.
     * @param order
     */
    public int makeOrder(Order order);
    
    /**
     * orderId에 대한 Order를 반환한다.
     * 이때 Order에 해당하는 OrderDetail에 대한 내용까지 반환한다.
     * OrderDetail의 내용은 id에 대한 내림차순으로 조회한다.
     * 
     * 3 단계에서는 selectOrderTotalInfo를 사용하고, 이 메소드를 사용하지 않음. 
     * @param orderId
     * @return
     */
    public Order getOrderDetails(Integer orderId);
    
    
    /**
     * id에 해당하는 사용자의 Order 목록을 주문 번호의 내림차순으로 반환한다.
     * 
     * @param id
     * @return
     */
    public List<Order> getOrderByUser(String id);
    
    /**
     * 주문 정보를 수정한다. - 주문의 상태만 변경된다.
     * @param order
     */
    public void updateOrder(Order order);
    
    /**
     * orderId에 대한 Order를 반환한다.
     * 이때 Order에 해당하는 OrderDetail에 대한 내용까지 반환한다.
     * OrderDetail의 내용은 detail id의 오름차순으로 조회한다.
     * 토탈금액, 상품명 등의 추가적인 정보가 담긴 OrderInfo객체를 리턴한다. 
     * @param id
     * @return
     */
    public OrderInfo getOrderInfo(Integer orderId);
    
    /**
     * 최근 1개월의 주문 내역을 반환한다.
     * 주문번호의 내림차순으로 조회된다. 
     * 주문번호로 조회된 order detail은 상세의 detail id의 오름차순으로 조회된다. 
     * 관통 6단계에서 추가됨
     * @param id
     * @return
     */
    List<OrderInfo> getLastMonthOrder(String id);

    /**
     * 최근 6개월의 주문 내역을 반환한다.
     * 주문번호로 조회된 order detail은 상세의 detail id의 오름차순으로 조회된다. 
     * 관통 6단계에서 추가됨
     * @param id
     * @return
     */
    List<OrderInfo> getLast6MonthOrder(String id);

    //전최 조회
    List<OrderInfo> getOrder();

    int updateOrderStatus(Long orderId,String statusCode);


}

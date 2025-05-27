package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetailInfo;
import com.ssafy.cafe.model.dto.OrderInfo;

public interface OrderDao {
    /**
     * order 테이블에 정보를 입력한다. 
     * auto_inctement된 id를 parameter로 전달되는 Order객체에 입력해야 한다.
     * 리턴되는 int는 JDBC에서 default로 리턴하는 값인 입력 건수이다. 
     *   
     * @param order
     * @return
     */
    int insert(Order order);

    /**
     * 파라미터로 전달받는 Order 객체에 들어있는 값 중에서, 
     * 주문 완료 칼럼만 (order_complete)만 update한다. 
     * 리턴되는 int는 JDBC에서 default로 리턴하는 값인 수정건수이다. 
     * 
     * @param order
     * @return
     */
    int update(Order order);

    /**
     * 사용자가 주문한 전체 주문정보를 주문번호 내림차순으로 리턴한다. 
     * 
     * @param userId
     * @return
     */
    Order selectByid(int id);
    
    List<Order> selectByUser(String userId);
    

    /**
     * orderId에 대한 Order와 OrderDetail에 대한 내용까지 반환한다.
     * 그리고, 추가적으로 토탈금액, 상품명 등의 추가적인 정보가 담긴 
     * OrderInfo객체를 리턴한다. 
     * OrderDetail의 내용은 detail id의 오름차순으로 조회한다.
     * 
     * @param id
     * @return
     */
    OrderInfo selectOrderInfo(int id); 
    
    /**
     * 사용자가 주문한 최근 1개월의 주문 주문번호 내림차순으로 조회된다. 
     * 주문번호의 상세내용은 detail id의 오름차순으로 조회된다. 
     * 관통 6단계에서 사용된다. 
     * 
     * @param id
     * @return
     */
    List<OrderInfo> getLastMonthOrder(String id);    
    
    /**
     * 사용자가 주문한 최근 6개월의 주문 주문번호 내림차순으로 조회된다. 
     * 주문번호의 상세내용은 detail id의 오름차순으로 조회된다. 
     * 관통 6단계에서 사용된다. 
     * 
     * @param id
     * @return
     */
    List<OrderInfo> getLast6MonthOrder(String id);   
    
    
    /**
     * orderId에 해당하는 주문 상세 정보를 조회한다. 
     * 주문상세는 detail id의 오름차순으로 정렬한다. 
     * 
     * @param orderId
     * @return
     */
    List<OrderDetailInfo> getOrderDetailInfo(int orderId);

    List<OrderInfo> getOrder();

    int updateOrderStatus(Long orderId, String statusCode);

}

package com.ssafy.cafe.model.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cafe.model.dao.OrderDao;
import com.ssafy.cafe.model.dao.OrderDetailDao;
import com.ssafy.cafe.model.dao.StampDao;
import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetail;
import com.ssafy.cafe.model.dto.OrderDetailInfo;
import com.ssafy.cafe.model.dto.OrderInfo;
import com.ssafy.cafe.model.dto.Stamp;
import com.ssafy.cafe.model.dto.User;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao oDao;
    
    @Autowired
    OrderDetailDao dDao;
    
    @Autowired
    StampDao sDao;
    
    @Autowired
    UserDao uDao;
    
    //f06
    @Transactional
    @Override
    public int makeOrder(Order order) {
        // TODO Auto-generated method stub
        // 오더 테이블에 주문내역 넣기
    	int order_rs=oDao.insert(order);
    	//오더 아이디 반환받기	
    	int order_id=oDao.selectByUser(order.getUserId()).get(0).getId();
    	
    	System.out.println("주문 아이디 : "+ order_id);
    	
    	//주문상세테이블에 가각의 정보 넣기
    	
    	int stamp_index=0;
    	for(OrderDetail data : order.getDetails()) {
    		data.setOrderId(order_id);
    		int detail_rs=dDao.insert(data);
    		Stamp stamp = new Stamp(order.getUserId(),order_id,data.getQuantity());
    		//t_ temp 에 넣기
    		int t_temp=sDao.insert(stamp);
    		stamp_index+=data.getQuantity();
    		
    	}
    	//유저 정보 테이블에 스템프 더하기
    	
    	User user=uDao.selectById(order.getUserId());
    	System.out.println(user.toString());
    	user.setStamps(user.getStamps()+stamp_index);
    	int user_up=uDao.updateStamp(user);
    	
    	return order_id;
    	
    }

    @Override
    public List<Order> getOrderByUser(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateOrder(Order order) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public OrderInfo getOrderInfo(Integer orderId) {
        // TODO Auto-generated method stub
    	
    	OrderInfo order =oDao.selectOrderInfo(orderId);
        return order;
    }

    @Override
    public List<OrderInfo> getLastMonthOrder(String id) {
        List<OrderInfo> info = oDao.getLastMonthOrder(id); 
        for (OrderInfo orderInfo : info) {
            List<OrderDetailInfo> detailInfo = oDao.getOrderDetailInfo(orderInfo.getId());
            orderInfo.setDetails(detailInfo);
        }
        
        return info;
    }
    
    @Override
    public List<OrderInfo> getLast6MonthOrder(String id) {
        return oDao.getLast6MonthOrder(id);
    }



    @Override
	public Order getOrderDetails(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<OrderInfo> getOrder() {
        return oDao.getOrder();
    }

    @Override
    public int updateOrderStatus(Long orderId, String statusCode) {
        return oDao.updateOrderStatus(orderId,statusCode);
    }


}

package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.OrderDao;
import com.ssafy.cafe.model.dao.UserDao;
import com.ssafy.cafe.model.dto.Order;
import com.ssafy.cafe.model.dto.OrderDetail;
import com.ssafy.cafe.model.dto.User;
import com.ssafy.cafe.model.dto.UserInfo;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao; 
    
    @Override
    public int join(User user) {
        // TODO Auto-generated method stub
        return userDao.insert(user);
    }

    @Override
    public User login(String id, String pass) {
        // TODO Auto-generated method stub
    	User user=new User(id,null, pass, null);
        return userDao.selectByUser(user);
    }

    @Override
    public boolean isUsedId(String id) {
        // TODO Auto-generated method stub
    	if(userDao.selectById(id) != null) {
    		return true;
    	} else {
    		return false;
    	}
    }

    @Override
    public User selectUser(String id) {
        // TODO Auto-generated method stub
        return userDao.selectById(id);
    }
    
    
	@Override
	public UserInfo userInfo(String id) {
		// TODO Auto-generated method stub
		//일단 유저  리턴
		User user=userDao.selectById(id);
		
		// 주문 내역 리턴
		List<Order> order = orderDao.selectByUser(id);
		UserInfo info= new UserInfo();
		info.setUser(user);
		info.setOrder(order);
		
		//컨틀로러에서 씨앗 정보를 가지고 메서드 이용해서 정리
		
		return info;
	}

    
}

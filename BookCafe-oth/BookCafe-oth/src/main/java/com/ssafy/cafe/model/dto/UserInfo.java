package com.ssafy.cafe.model.dto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInfo {
	
	private Grade grade;
	private User user;
	private List<Order> order;
	
    public UserInfo() {

	}

	
    public UserInfo(Grade grade, User user, List<Order> order) {
		super();
		this.grade = grade;
		this.user = user;
		this.order = order;
	}

	public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

}
